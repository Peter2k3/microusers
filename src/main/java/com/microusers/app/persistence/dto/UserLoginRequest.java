package com.microusers.app.persistence.dto;

import lombok.Data;

@Data
public class UserLoginRequest {
    private String email;
    private String password;
}
