package com.koomineat.koomineat.global.domain.auth.controller;

import com.koomineat.koomineat.global.domain.auth.entity.User;
import com.koomineat.koomineat.global.domain.auth.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestParam String name, HttpServletResponse response) {
        User user = userService.registerAndIssueCookie(name, response);
        return ResponseEntity
                .status(201).
                body(Map.of("message", "register success"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@CookieValue(name = UserService.COOKIE_NAME, required = false) String authToken) {
        boolean ok = userService.authenticateByCookie(authToken);
        if (!ok) {
            return ResponseEntity.status(401).body("unauthorized");
        }
        return ResponseEntity.ok("success!");
    }

    // 디버그용. user를 잘 찾나 확인.
    @GetMapping("/getUser")
    public ResponseEntity<?> getUser(@CookieValue(name = UserService.COOKIE_NAME, required = false) String authToken)
    {
        User user = userService.getUserFromAuthToken(authToken);

        return ResponseEntity
                .status(200)
                .body(user);
    }
}
