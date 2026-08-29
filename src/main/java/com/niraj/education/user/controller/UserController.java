package com.niraj.education.user.controller;

import com.niraj.education.user.dto.LoginRequest;
import com.niraj.education.user.entity.User;
import com.niraj.education.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.niraj.education.user.security.JWTService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    @PostMapping("/register")
    public ResponseEntity<User>rregister(@RequestBody User user){
        return ResponseEntity.ok(userService.register(user));
    }


    @PostMapping("/login")
    public ResponseEntity<String>Login(@RequestBody LoginRequest request){

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(), request.getPassword())
        );
    String token=jwtService.generateToken(
            authentication.getName()
    );
            return ResponseEntity.ok(token);
    }
}
