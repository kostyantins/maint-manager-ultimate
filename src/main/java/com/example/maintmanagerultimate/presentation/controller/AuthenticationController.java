package com.example.maintmanagerultimate.presentation.controller;

import com.example.maintmanagerultimate.service.configuration.JwtUtils;
import com.example.maintmanagerultimate.service.dao.UserDao;
import com.example.maintmanagerultimate.service.dto.AuthenticationRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final UserDao userDao;
    private final JwtUtils jwtUtils;

    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticate(@RequestBody AuthenticationRequestDto authenticationRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getEmail(),
                        authenticationRequest.getPassword()));

        final UserDetails userDetails = userDao.loadUserByEmail(authenticationRequest.getEmail());

        if (userDetails != null) {
            return ResponseEntity.ok(jwtUtils.generateToken(userDetails));
        }

        return ResponseEntity.status(400).body("Something wrong with the user %s".formatted(userDetails));
    }
}
