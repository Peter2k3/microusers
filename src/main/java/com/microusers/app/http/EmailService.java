package com.microusers.app.http;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.microusers.app.persistence.dto.UserVerificationDTO;

@Service
public class EmailService {

    private String emailServiceUrl = "http://localhost:8081/v1/confirm-mail/send-html";
    
    private final RestTemplate restTemplate;

    public EmailService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String sendVerificationEmail(UserVerificationDTO userVerificationDTO) {
        // Define la URL base y el endpoint en el microservicio de correos
        String url = emailServiceUrl;

        // Realiza una solicitud POST, enviando el DTO en el cuerpo
        return restTemplate.postForObject(url, userVerificationDTO, String.class);
    }

}
