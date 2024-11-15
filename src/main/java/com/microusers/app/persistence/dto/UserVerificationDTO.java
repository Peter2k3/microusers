package com.microusers.app.persistence.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVerificationDTO {

    Integer idUserVerification;

    int code;
    
    private UserEntityDTO user;

}
