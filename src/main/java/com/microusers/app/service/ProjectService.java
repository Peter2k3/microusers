package com.microusers.app.service;

import com.microusers.app.persistence.dto.ProjectAndUsersDTO;
import com.microusers.app.persistence.dto.UserEntityDTO;
import com.microusers.app.persistence.entity.ProjectEntity;
import com.microusers.app.persistence.entity.UserEntity;
import com.microusers.app.persistence.mapper.UserMapper;
import com.microusers.app.persistence.repository.ProjectRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;


    public void saveProject(ProjectEntity projectsEntity){
        //establecemos un identificador global al projecto
        UUID uuid = UUID.randomUUID();
        projectsEntity.setUuid(uuid);
        //establecemos la fecha de creacion a la fecha y hora actual
        LocalDateTime fechaCreacion= LocalDateTime.now();
        projectsEntity.setFechaCreacion(fechaCreacion);

        if (projectsEntity.getCustomEmail().isEmpty()) { //Si la entidad no viene con email entonces se guarda con null
            projectsEntity.setCustomEmail(null);
        }

        projectRepository.save(projectsEntity);
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


}
