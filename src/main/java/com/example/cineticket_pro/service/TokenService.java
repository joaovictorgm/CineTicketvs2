package com.example.cineticket_pro.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

    @Value("spring.secret")
    private String secret;

    @Value("spring.expiracao")
    private Long expiracao;

    @Value("spring.emissor")
    private String emissor;

    public String gerarToken(String subject) {

        try {

        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}
