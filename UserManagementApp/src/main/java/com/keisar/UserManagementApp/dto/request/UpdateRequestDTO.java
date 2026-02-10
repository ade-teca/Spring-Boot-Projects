package com.keisar.UserManagementApp.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateRequestDTO {

    private String username;

    @Email
    private String email;
}
