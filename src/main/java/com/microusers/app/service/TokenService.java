package com.microusers.app.service;

import java.security.SecureRandom;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microusers.app.persistence.entity.ProjectEntity;
import com.microusers.app.persistence.entity.TokenEntity;
import com.microusers.app.persistence.repository.TokenEntityRepository;

@Service
public class TokenService {

    @Autowired
    TokenEntityRepository tokenRepository;
    @Autowired
    ProjectService projectService;
    private static final SecureRandom secureRandom = new SecureRandom(); // threadsafe
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder(); // threadsafe

    public static String generateToken(int length) {
        byte[] randomBytes = new byte[length];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }

    public String createToken(Integer idProject) {
        String token = generateToken(30);
        ProjectEntity project = projectService.findProjectEntityByIdProjectEntity(idProject)
            .orElseThrow(() -> new RuntimeException("Proyecto no encontrado con ID: " + idProject));
        
        TokenEntity tokenEntity = TokenEntity.builder()
                                         .status("active")
                                         .token(token)
                                         .proyecto(project)
                                         .build();
        tokenRepository.save(tokenEntity);
        project.getTokens().add(tokenEntity);
        return token;
    }


}
