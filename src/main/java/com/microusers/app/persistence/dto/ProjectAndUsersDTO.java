package com.microusers.app.persistence.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.microusers.app.persistence.entity.UserEntity;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
public class ProjectAndUsersDTO {

    private Integer idProyecto;

    private String nombre;

    private UUID uuid;

    private LocalDateTime fechaCreacion;

    private String customEmail;

    private Set<UserEntityDTO> usuarios;

}
