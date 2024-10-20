package com.microusers.app.controller;

import com.microusers.app.persistence.dto.ProjectAndUsersDTO;
import com.microusers.app.persistence.dto.TokenRequestDTO;
import com.microusers.app.persistence.entity.ProjectEntity;
import com.microusers.app.persistence.entity.UserEntity;
import com.microusers.app.service.ProjectService;
import com.microusers.app.service.TokenService;
import com.microusers.app.service.UserService;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("v1/projects")
@PreAuthorize("permitAll()")
public class ProjectController {

    @Autowired
    ProjectService projectService;
    @Autowired
    UserService userService;
    @Autowired
    TokenService tokenService;

    @PostMapping
    public ResponseEntity<?> saveProject (@RequestParam Integer idUsuario, @RequestParam String email,
                                          @RequestParam String projectName){
        ProjectEntity project = new ProjectEntity();

        project.setCustomEmail(email);
        project.setNombre(projectName);
        projectService.saveProject(project, idUsuario);
        return ResponseEntity.status(HttpStatus.CREATED).body("El proyecto se creo con exito");
    }

    //@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PreAuthorize("permitAll()")
    @GetMapping("/{idProyecto}")
    public ResponseEntity<ProjectAndUsersDTO> getProjectAndUsers(@PathVariable Integer idProyecto) {
        ProjectAndUsersDTO projectAndUsers = projectService.projectAndUsers(idProyecto);
        return ResponseEntity.ok(projectAndUsers);
    }

    
    
}