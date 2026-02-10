package com.keisar.UserManagementApp.service;


import com.keisar.UserManagementApp.dto.request.ChangePasswordDTO;
import com.keisar.UserManagementApp.dto.request.UpdateRequestDTO;
import com.keisar.UserManagementApp.dto.response.UserDTO;
import com.keisar.UserManagementApp.model.User;
import com.keisar.UserManagementApp.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;


    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(()-> new RuntimeException("User not found"));
        return modelMapper.map(user, UserDTO.class);
    }

    public UserDTO getUserByIdentifier(String identifier) {
        User user = userRepository.findByUsernameOrEmail(identifier, identifier)
                .orElseThrow(() -> new RuntimeException("User not found with identifier: " + identifier));
        return modelMapper.map(user, UserDTO.class);
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public UserDTO changePassword(Long id, ChangePasswordDTO  changePasswordDTO) {
        User user = userRepository.findById(id).orElseThrow(()-> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(changePasswordDTO.getOldPassword(), user.getPassword())) {
            throw new RuntimeException("Old Password Doesn't Match");
        }
        if (!changePasswordDTO.getNewPassword().equals(changePasswordDTO.getConfirmPassword())) {
            throw new RuntimeException("New Password Doesn't Match with confirm Password");
        }

        user.setPassword(passwordEncoder.encode(changePasswordDTO.getNewPassword()));
        return modelMapper.map(userRepository.save(user), UserDTO.class);
    }

    @Transactional
    public UserDTO updateUser(Long id, UpdateRequestDTO updateRequestDTO) {
        User user = userRepository.findById(id).orElseThrow(()-> new RuntimeException("User not found"));

        if (updateRequestDTO.getEmail() != null && !updateRequestDTO.getEmail().equals(user.getEmail())) {
            if (userRepository.existsByEmail(updateRequestDTO.getEmail())) {
                throw new RuntimeException("Email Already Exists");
            }
        }
        modelMapper.map(updateRequestDTO, user);
        return modelMapper.map(userRepository.save(user), UserDTO.class);
    }

    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(id);
    }

}
