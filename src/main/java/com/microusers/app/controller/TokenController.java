package com.microusers.app.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microusers.app.persistence.dto.TokenRequestDTO;
import com.microusers.app.persistence.entity.ProjectEntity;
import com.microusers.app.persistence.entity.TokenEntity;
import com.microusers.app.persistence.entity.UserEntity;
import com.microusers.app.service.ProjectService;
import com.microusers.app.service.TokenService;
import com.microusers.app.service.UserService;

@RestController
@RequestMapping("/v1/tokens")
public class TokenController {

    @Autowired
    UserService userService;
    @Autowired
    TokenService tokenService;
    @Autowired
    ProjectService projectService;


    @PostMapping("/create-token")
    public ResponseEntity<?> createToken(@RequestBody TokenRequestDTO tokenRequest) {
        UserEntity user = tokenRequest.getUser();
        ProjectEntity projectEntity = tokenRequest.getProjectEntity();

        if (user.getIdUsuario() == null) return ResponseEntity.badRequest().body("idUsuarioNulo");

        Optional<UserEntity> userVerify = userService.findUserById(user.getIdUsuario());

        if (userVerify.isEmpty())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se puede encontrar al usuario");

        if (userVerify.get().getProjecs().contains(projectEntity))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usted no puede crear tokens para este proyecto");

        String tokenString = tokenService.createToken(projectEntity.getIdProyecto());
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(tokenString);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteToken(@RequestBody TokenRequestDTO tokenRequestDTO){
        Optional<TokenEntity> token = tokenService.findByToken(tokenRequestDTO.getTokenEntity().getToken());
        Optional<ProjectEntity> project = projectService
            .findProjectEntityByIdProjectEntity(tokenRequestDTO.getProjectEntity().getIdProyecto());

        if (token.isEmpty() | project.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalido el token: " + token.isEmpty() + " El proyecto: " + project.isEmpty());
        }
        if (!token.get().getProyecto().getIdProyecto().equals(project.get().getIdProyecto())) {
            return ResponseEntity.badRequest().body("El token no corresponde al proyecto: " +  project.get().getIdProyecto());
        }
        boolean isFree = "free".equals(project.get().getTypeOfPlan());

        if (isFree) {
            tokenService.deletePhysically(token.get().getIdToken());
        } else {
            tokenService.deleteLogically(token.get());
        }
        return ResponseEntity.ok().body("El token se borró correctamente");
    }

}
