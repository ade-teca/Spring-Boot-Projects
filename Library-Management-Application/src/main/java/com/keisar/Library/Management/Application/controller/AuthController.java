package com.keisar.Library.Management.Application.controller;

import com.keisar.Library.Management.Application.dto.request.LoginUserDTO;
import com.keisar.Library.Management.Application.dto.request.RegisterUserDTO;
import com.keisar.Library.Management.Application.dto.response.LoginResponse;
import com.keisar.Library.Management.Application.dto.response.UserResponseDTO;
import com.keisar.Library.Management.Application.model.User;
import com.keisar.Library.Management.Application.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registerNormalUser(@RequestBody RegisterUserDTO userDTO) {
        return ResponseEntity.status(201).body(authService.registerNormalUser(userDTO));
    }

    @PostMapping
    public ResponseEntity<LoginResponse> loginUser(@RequestBody LoginUserDTO loginUserDTO) {
        return ResponseEntity.ok(authService.login(loginUserDTO));
    }
}
