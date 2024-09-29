package com.microusers.app.persistence.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.microusers.app.persistence.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Builder
@Setter
@Getter
public class ProjectDTO {

    private Integer idProyecto;

    private String nombre;

    private UUID uuid;

    private LocalDateTime fechaCreacion;

    private String customEmail;

    @JsonIgnore
    private Set<UserEntity> usuarios;

}
