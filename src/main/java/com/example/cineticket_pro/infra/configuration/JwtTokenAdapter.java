package com.example.cineticket_pro.infra.configuration;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.cineticket_pro.application.port.TokenPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
public class JwtTokenAdapter implements TokenPort {

    @Value("${spring.secretkey}")
    private String secret;

    @Value("${spring.tempo_expiracao}")
    private Long expiracao;

    private static final String EMISSOR = "TESTADOR";

    @Override
    public String gerar(String assunto) {
        return JWT.create()
                .withIssuer(EMISSOR)
                .withSubject(assunto)
                .withExpiresAt(Instant.now().plus(expiracao, ChronoUnit.MINUTES))
                .sign(Algorithm.HMAC256(secret));
    }

    @Override
    public void verificar(String token) {
        try {
            JWT.require(Algorithm.HMAC256(secret)).withIssuer(EMISSOR).build().verify(token);
        } catch (JWTVerificationException e) {
            throw new IllegalArgumentException("Token inválido", e);
        }
    }
}