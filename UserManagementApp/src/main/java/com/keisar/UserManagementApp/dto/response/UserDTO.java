package com.keisar.UserManagementApp.dto.response;

import lombok.Data;

import java.util.Set;

@Data
public class UserDTO {

    private Long id;

    private String username;
    private String email;
    private String password;
    private Set<String> roles;
}
