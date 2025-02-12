package ru.kata.spring.boot_security.demo.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

    // Проверка аутентификации
    @PostMapping("/login")
    public ResponseEntity<?> checkLogin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).body(Map.of("error", "Unauthorized"));
        }
        return ResponseEntity.ok(Map.of("message", "User is authenticated", "user", authentication.getName()));
    }

    // Обработка ошибки логина
    @GetMapping("/login-error")
    public ResponseEntity<?> loginError() {
        return ResponseEntity.status(401).body(Map.of("error", "Invalid username or password"));
    }

    // Выход из системы (если используется сессия)
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.logout(); // Официальный способ выхода
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok(Map.of("message", "Logged out successfully"));
    }
}
