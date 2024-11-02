package com.microusers.app.persistence.dto;

import lombok.Data;

@Data
public class UserVerificationDTO {

    Integer idUserVerification;

    int code;
    
    private UserEntityDTO user;

}
