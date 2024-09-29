package com.microusers.app.controller;

import com.microusers.app.persistence.dto.ProjectAndUsersDTO;
import com.microusers.app.persistence.entity.ProjectEntity;
import com.microusers.app.persistence.entity.UserEntity;
import com.microusers.app.persistence.repository.ProjectRepository;
import com.microusers.app.service.ProjectService;
import com.microusers.app.service.UserService;
import jakarta.persistence.PrePersist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("v1/projects")
@PreAuthorize("permitAll()")
public class ProjectController {

    @Autowired
    ProjectService projectService;
    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity<?> saveProject (@RequestBody ProjectEntity projectEntity){
        if (projectEntity.getNombre().isEmpty() & projectEntity.getUsuarios().isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("El nombre del proyecto ni sus usuarios no puede estar vacio");
        }

        Integer idUsuario = projectEntity.getUsuarios().stream().findFirst().get().getIdUsuario();
        Optional<UserEntity> user = userService.findUserById(idUsuario);
        Set<UserEntity> setUser = new HashSet<>();
        projectEntity.setUsuarios(setUser);
        projectService.saveProject(projectEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body("El proyecto se creo con exito");
    }

    //@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PreAuthorize("permitAll()")
    @GetMapping("/{idProyecto}")
    public ProjectAndUsersDTO projectAndUsersDTO(Integer idProyecto){
        return projectService.projectAndUsers(idProyecto);
    }
}