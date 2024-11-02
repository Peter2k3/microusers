package com.microusers.app.persistence.dto;


import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@Data
public class UserEntityDTO {

    private Integer idUsuario;

    private String email;

}
