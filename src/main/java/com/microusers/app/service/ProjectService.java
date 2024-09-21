package com.microusers.app.service;

import com.microusers.app.persistence.entity.ProjectEntity;
import com.microusers.app.persistence.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

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
}
