package com.koomineat.koomineat.domain.auth.service;

import com.koomineat.koomineat.domain.auth.entity.User;
import com.koomineat.koomineat.domain.auth.repository.UserRepository;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    public static final String COOKIE_NAME = "AUTH_TOKEN";

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User registerAndIssueCookie(String name, HttpServletResponse response) {
        // 토큰 랜덤 생성
        String token = UUID.randomUUID().toString();

        // user 생성.
        User user = User.builder()
                .name(name)
                .authToken(token)
                .build();
        user = userRepository.save(user);


        // set cookie. 배포시에 수정해야 함.
        ResponseCookie cookie = ResponseCookie.from(COOKIE_NAME, token)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(Duration.ofDays(30))
                .sameSite("Lax")
                .build();

        response.addHeader("Set-Cookie", cookie.toString());
        return user;
    }

    // 그냥 user만 있다면 return true.
    @Transactional(readOnly = true)
    public boolean authenticateByCookie(String tokenFromCookie) {
        if (tokenFromCookie == null || tokenFromCookie.isBlank()) return false;
        return userRepository.findByAuthToken(tokenFromCookie).isPresent();
    }
    
    // authToken으로 user를 가져온다.
    public User getUserFromAuthToken(String authToken)
    {
        return userRepository.findByAuthToken(authToken).orElseThrow(() -> new RuntimeException("Invalid auth token"));
    }


}
