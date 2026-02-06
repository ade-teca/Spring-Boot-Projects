package com.keisar.Library.Management.Application.service;

import com.keisar.Library.Management.Application.dto.request.RegisterUserDTO;
import com.keisar.Library.Management.Application.dto.response.UserResponseDTO;
import com.keisar.Library.Management.Application.model.User;
import com.keisar.Library.Management.Application.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public UserResponseDTO updateUser(Long id, RegisterUserDTO registerUserDTO) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        modelMapper.map(registerUserDTO, existingUser);
        existingUser.setId(id);

        if (registerUserDTO.getPassword() != null && !registerUserDTO.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(registerUserDTO.getPassword()));
        }

        User updatedUser = userRepository.save(existingUser);
        return modelMapper.map(updatedUser, UserResponseDTO.class);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(id);
    }
}
