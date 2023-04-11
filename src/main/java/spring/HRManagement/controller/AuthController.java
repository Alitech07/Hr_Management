package spring.HRManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.*;
import spring.HRManagement.config.SecurityConfig;
import spring.HRManagement.payload.ApiResponse;
import spring.HRManagement.payload.LoginDto;
import spring.HRManagement.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    SecurityConfig securityConfig;
    @Autowired
    AuthService authService;

    @PostMapping("/login")
    public HttpEntity<?> login(@RequestBody LoginDto loginDto){
        ApiResponse login = authService.login(loginDto);
        return ResponseEntity.status(login.isSuccess()?200:409).body(login);
    }


}
