package com.keisar.Library.Management.Application.dto.request;

import lombok.Data;

@Data
public class RegisterUserDTO {
    private  String username;
    private String password;
    private String email;
}
