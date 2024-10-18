package com.microusers.app.service;

import com.microusers.app.persistence.dto.ProjectAndUsersDTO;
import com.microusers.app.persistence.dto.UserEntityDTO;
import com.microusers.app.persistence.entity.ProjectEntity;
import com.microusers.app.persistence.entity.UserEntity;
import com.microusers.app.persistence.mapper.UserMapper;
import com.microusers.app.persistence.repository.ProjectRepository;
import com.microusers.app.persistence.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    UserRepository userRepository;

    public void saveProject(ProjectEntity projectEntity, Integer idUsuario){
        UserEntity user = userRepository.findById(idUsuario).orElseThrow(()-> new RuntimeException("Usuario Invalido"));

        projectEntity.setUuid(UUID.randomUUID());
        projectEntity.setFechaCreacion(LocalDateTime.now());
        System.out.println(user.getEmail());
        Set<UserEntity> usuario = new HashSet<>();
        usuario.add(user);
        projectEntity.setUsuarios(usuario);
        user.getProjecs().add(projectEntity);

        projectRepository.save(projectEntity);
    }

    public ProjectAndUsersDTO projectAndUsers(Integer idProject) {
        Optional<ProjectEntity> proyectoConUsuarios = projectRepository.findById(idProject);

        if(proyectoConUsuarios.isPresent()) {

            //Esta funcion crea la lista de usuarios y las convierte a tipo DTO
            Set<UserEntityDTO> usuarios = proyectoConUsuarios.get().getUsuarios().stream()
                    .map(UserMapper::toUserEntityDTo)
                    .collect(Collectors.toSet());

            return ProjectAndUsersDTO.builder()
                    .usuarios(usuarios)
                    .customEmail(proyectoConUsuarios.get().getCustomEmail())
                    .fechaCreacion(proyectoConUsuarios.get().getFechaCreacion())
                    .idProyecto(proyectoConUsuarios.get().getIdProyecto())
                    .uuid(proyectoConUsuarios.get().getUuid())
                    .nombre(proyectoConUsuarios.get().getNombre())
                    .build();
        } else {
            throw new EntityNotFoundException("El proyecto con el id " + idProject + " no existe");
        }
    }

    public Optional<ProjectEntity> findProjectEntityByIdProjectEntity(Integer idProject){
        return projectRepository.findById(idProject);
    }


}
