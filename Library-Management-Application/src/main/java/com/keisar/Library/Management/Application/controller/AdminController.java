package com.keisar.Library.Management.Application.controller;


import com.keisar.Library.Management.Application.dto.request.RegisterUserDTO;
import com.keisar.Library.Management.Application.dto.response.UserResponseDTO;
import com.keisar.Library.Management.Application.model.User;
import com.keisar.Library.Management.Application.service.AdminService;
import com.keisar.Library.Management.Application.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;
    private final AuthService authService;


    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registerAdmin(@RequestBody RegisterUserDTO userDTO) {
        return ResponseEntity.status(201).body(authService.registerAdminUser(userDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id, @RequestBody RegisterUserDTO userDTO) {
        return ResponseEntity.ok(adminService.updateUser(id, userDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        adminService.deleteUser(id);
        return ResponseEntity.status(204).build();
    }
}
