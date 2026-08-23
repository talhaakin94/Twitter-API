package com.app.twitterapi.controller;
import com.app.twitterapi.dto.LoginResponse;
import com.app.twitterapi.dto.LoginRequest;
import com.app.twitterapi.dto.RegisterRequest;
import com.app.twitterapi.entity.User;
import com.app.twitterapi.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }
    @PostMapping("/register")
    public User register(@Valid @RequestBody RegisterRequest registerRequest) {
        return authenticationService.register(registerRequest.name(), registerRequest.email(), registerRequest.password());
    }
    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest loginRequest) {
        User user = authenticationService.login(loginRequest.email(), loginRequest.password());
        return new LoginResponse(user.getId(), user.getEmail(), user.getPassword());
    }
}
