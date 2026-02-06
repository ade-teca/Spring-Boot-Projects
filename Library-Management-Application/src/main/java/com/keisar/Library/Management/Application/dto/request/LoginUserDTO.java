package com.keisar.Library.Management.Application.dto.request;

import lombok.Data;

@Data
public class LoginUserDTO {
    private  String password;
    private String email;
}
