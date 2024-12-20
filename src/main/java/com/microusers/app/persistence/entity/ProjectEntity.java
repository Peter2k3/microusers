package com.microusers.app.persistence.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.UUID;


@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "proyectos")
public class ProjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProyecto;

    private String nombre;

    private UUID uuid;

    private LocalDateTime fechaCreacion;

    private String customEmail;

    private String typeOfPlan;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "proyectos_usuarios", joinColumns = @JoinColumn(name = "id_proyecto"), inverseJoinColumns = @JoinColumn(name = "id_usuario"))
    @Builder.Default
    private Set<UserEntity> usuarios= new HashSet<>();

    @OneToMany(mappedBy = "proyecto")
    private List<TokenEntity> tokens;

}