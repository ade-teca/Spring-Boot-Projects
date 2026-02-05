package com.keisar.Library.Management.Application.dto.response;

import lombok.Data;

@Data
public class LoginResponse {
    private String token;
    private String username;
}
