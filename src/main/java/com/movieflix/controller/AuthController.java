package com.movieflix.controller;

import com.movieflix.controller.request.LoginRequest;
import com.movieflix.controller.request.UserRequest;
import com.movieflix.controller.response.LoginResponse;
import com.movieflix.controller.response.UserResponse;
import com.movieflix.entity.User;
import com.movieflix.security.TokenService;
import com.movieflix.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movieflix/auth")
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register (@RequestBody UserRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(request));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login (@RequestBody LoginRequest request) {
        UsernamePasswordAuthenticationToken userAndPass = new UsernamePasswordAuthenticationToken(request.email(),request.password());
        Authentication authentication = authenticationManager.authenticate(userAndPass);

        User user = (User) authentication.getPrincipal();

        String token = tokenService.generateToken(user);

        return ResponseEntity.ok(
                LoginResponse
                        .builder()
                        .token(token)
                        .build());
    }
}
