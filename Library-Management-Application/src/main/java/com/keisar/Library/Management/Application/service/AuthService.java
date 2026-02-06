package com.keisar.Library.Management.Application.service;

import com.keisar.Library.Management.Application.dto.request.LoginUserDTO;
import com.keisar.Library.Management.Application.dto.request.RegisterUserDTO;
import com.keisar.Library.Management.Application.dto.response.LoginResponse;
import com.keisar.Library.Management.Application.dto.response.UserResponseDTO;
import com.keisar.Library.Management.Application.model.User;
import com.keisar.Library.Management.Application.repository.UserRepository;
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

    public User registerAdminUser(RegisterUserDTO registerUserDTO) {
        saveUser(registerUserDTO, Set.of("ROLE_USER", "ROLE_ADMIN"));
        return modelMapper.map(registerUserDTO, User.class);
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
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUserDTO.getEmail(),
                        loginUserDTO.getPassword()
                )
        );

        User user = userRepository.findByEmail(loginUserDTO.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found after authentication"));

        String token = jwtService.generateToken(user);

        return new LoginResponse(token, user.getEmail());
    }
}