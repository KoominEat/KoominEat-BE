package com.koomineat.koomineat.domain.auth.service;

import com.koomineat.koomineat.domain.auth.entity.User;
import com.koomineat.koomineat.domain.auth.repository.UserRepository;
import com.koomineat.koomineat.global.exception.ErrorCode;
import com.koomineat.koomineat.global.exception.KookminEatException;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    private final Environment env;

    private final UserRepository userRepository;
    public static final String COOKIE_NAME = "AUTH_TOKEN";

    public UserService(Environment env, UserRepository userRepository) {
        this.env = env;
        this.userRepository = userRepository;
    }

    private boolean isProd() {
        return List.of(env.getActiveProfiles()).contains("prod");
    }

    @Transactional
    public User registerAndIssueCookie(String name, HttpServletResponse response) {
        boolean prod = isProd();
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
                .secure(prod)
                .path("/")
                .maxAge(Duration.ofDays(30))
                .sameSite(prod ? "None" : "Lax")
                .build();

        response.addHeader("Set-Cookie", cookie.toString());
        return user;
    }

    @Transactional(readOnly = true)
    public boolean authenticateByCookie(String authToken) {
        if (authToken == null || authToken.isBlank()) return false;
        return userRepository.findByAuthToken(authToken).isPresent();
    }
    
    // authToken으로 user를 가져온다.
    public User getUserFromAuthToken(String authToken) {
        return userRepository.findByAuthToken(authToken).orElseThrow(() -> new KookminEatException(ErrorCode.INVALID_AUTH_TOKEN));
    }


}
