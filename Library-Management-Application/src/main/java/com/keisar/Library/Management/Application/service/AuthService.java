package com.keisar.Library.Management.Application.service;

import com.keisar.Library.Management.Application.dto.request.LoginUserDTO;
import com.keisar.Library.Management.Application.dto.request.RegisterUserDTO;
import com.keisar.Library.Management.Application.dto.response.LoginResponse;
import com.keisar.Library.Management.Application.dto.response.UserResponseDTO;
import com.keisar.Library.Management.Application.model.User;
import com.keisar.Library.Management.Application.repository.UserRepository;
import com.keisar.Library.Management.Application.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final ModelMapper modelMapper;
    private final JwtService jwtService;

    public UserResponseDTO registerNormalUser(RegisterUserDTO registerUserDTO) {
        User savedUser = saveUser(registerUserDTO, Set.of("ROLE_USER"));
        return modelMapper.map(savedUser, UserResponseDTO.class);
    }

    public UserResponseDTO registerAdminUser(RegisterUserDTO registerUserDTO) {
        saveUser(registerUserDTO, Set.of("ROLE_USER", "ROLE_ADMIN"));
        return modelMapper.map(registerUserDTO, UserResponseDTO.class);
    }

    private User saveUser(RegisterUserDTO dto, Set<String> roles) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email already in use");
        }

        User user = modelMapper.map(dto, User.class);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRoles(roles);
        userRepository.save(user);
        return user;
    }

    public LoginResponse login(LoginUserDTO loginUserDTO) {
        // 1. Autentica
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUserDTO.getEmail(),
                        loginUserDTO.getPassword()
                )
        );

        // 2. O 'principal' após a autenticação já contém o UserDetails (
        User user = (User) authentication.getPrincipal();

        // 3. Gera o token
        String token = jwtService.generateToken(user);

        return new LoginResponse(token, user.getEmail());
    }
}