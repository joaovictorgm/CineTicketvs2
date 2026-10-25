package com.example.cineticket_pro.application.service;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.cineticket_pro.application.DTOs.LoginRequest;
import com.example.cineticket_pro.application.DTOs.UsuarioLogado;
import com.example.cineticket_pro.domain.entities.Token;
import com.example.cineticket_pro.domain.repository.TokenRepository;
import com.example.cineticket_pro.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${spring.secretkey}")
    private String secret;

    @Value("${spring.tempo_expiracao}")
    private Long expiracao;

    private String emissor = "TESTADOR";

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TokenRepository tokenRepository;

    public String gerarToken(LoginRequest loginRequest) {


            Algorithm algorithm = Algorithm.HMAC256(secret);

            var usuario = usuarioRepository.findAll().stream().filter(u -> u.getEmail().equals(loginRequest.email())).findFirst().orElse(null);

            String token = JWT.create()
                    .withIssuer(emissor)
                    .withSubject(loginRequest.email())
                    .withExpiresAt(this.getDataExpiracao())
                    .sign(algorithm);

                    tokenRepository.save(new Token(token, usuario));
            return token;


    }

    public UsuarioLogado verificarToken(String token) throws JWTVerificationException {
        Algorithm algorithm = Algorithm.HMAC256(secret);

        JWTVerifier verificador = JWT.require(algorithm).withIssuer(emissor).build();

        verificador.verify(token);

        var tokenBanco = tokenRepository.findTokenByToken(token).orElse(null);

        if (tokenBanco == null) {
            throw new IllegalArgumentException("Token Invalido!");
        }

        return new UsuarioLogado(tokenBanco.getUsuario());
    }




    private Instant getDataExpiracao(){
        // pegar data atual
        var dataAtual = LocalDateTime.now();
        //adicionar ou diminuir tempo da data atual
        var dataFutura = dataAtual.plusMinutes(expiracao);

        //Converter em instant
        return dataFutura.toInstant(ZoneOffset.of("-03:00"));
    }

    }