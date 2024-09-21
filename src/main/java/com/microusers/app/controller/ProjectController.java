package com.microusers.app.controller;

import com.microusers.app.persistence.entity.ProjectEntity;
import com.microusers.app.persistence.entity.UserEntity;
import com.microusers.app.persistence.repository.ProjectRepository;
import com.microusers.app.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/projects")
@PreAuthorize("permitAll()")
public class ProjectController {

    @Autowired
    ProjectService projectService;

    @PostMapping
    public ResponseEntity<?> saveProject (@RequestBody ProjectEntity projectEntity){

        if (projectEntity.getNombre().isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("El nombre del proyecto no puede estar vacio");
        }

        projectService.saveProject(projectEntity);


        return ResponseEntity.status(HttpStatus.CREATED).body("El proyecto se creo con exito");
    }
    
}
