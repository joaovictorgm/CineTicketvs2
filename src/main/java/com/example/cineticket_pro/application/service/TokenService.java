package com.example.cineticket_pro.application.service;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.cineticket_pro.application.DTOs.LoginRequest;
import com.example.cineticket_pro.application.DTOs.UsuarioLogado;
import com.example.cineticket_pro.domain.entities.Token;
import com.example.cineticket_pro.domain.repository.GerenteRepository;
import com.example.cineticket_pro.domain.repository.TokenRepository;
import com.example.cineticket_pro.domain.repository.UsuarioRepository;
import com.example.cineticket_pro.domain.valueobject.Email;
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
    private GerenteRepository gerenteRepository;

    @Autowired
    private TokenRepository tokenRepository;

    public String gerarToken(LoginRequest loginRequest) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        String email = new Email(loginRequest.email()).toString();

        var usuario = usuarioRepository.findByEmail(email).orElse(null);
        var gerente = gerenteRepository.findByEmail(email).orElse(null);

        String token = JWT.create()
                .withIssuer(emissor)
                .withSubject(email)
                .withExpiresAt(this.getDataExpiracao())
                .sign(algorithm);

        if (usuario != null) {
            tokenRepository.save(new Token(token, usuario));
        } else if (gerente != null) {
            tokenRepository.save(new Token(token, gerente));
        }
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

        if (tokenBanco.getUsuario() != null) {
            return new UsuarioLogado(tokenBanco.getUsuario());     // era getGerente()
        }
        if (tokenBanco.getGerente() != null) {
            return new UsuarioLogado(tokenBanco.getGerente());
        }
        throw new IllegalArgumentException("Token Invalido!");


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