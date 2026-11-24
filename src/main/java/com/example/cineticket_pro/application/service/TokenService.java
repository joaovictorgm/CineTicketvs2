package com.example.cineticket_pro.application.service;

import com.example.cineticket_pro.application.DTOs.LoginRequest;
import com.example.cineticket_pro.application.DTOs.UsuarioLogado;
import com.example.cineticket_pro.application.port.TokenPort;
import com.example.cineticket_pro.domain.entities.Token;
import com.example.cineticket_pro.domain.repository.GerenteRepository;
import com.example.cineticket_pro.domain.repository.TokenRepository;
import com.example.cineticket_pro.domain.repository.UsuarioRepository;
import com.example.cineticket_pro.domain.valueobject.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

    @Autowired
    private TokenPort tokenPort;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private GerenteRepository gerenteRepository;

    @Autowired
    private TokenRepository tokenRepository;

    public String gerarToken(LoginRequest loginRequest) {
        String email = new Email(loginRequest.email()).toString();

        var usuario = usuarioRepository.findByEmail(email).orElse(null);
        var gerente = gerenteRepository.findByEmail(email).orElse(null);

        String token = tokenPort.gerar(email);

        if (usuario != null) {
            tokenRepository.save(new Token(token, usuario));
        } else if (gerente != null) {
            tokenRepository.save(new Token(token, gerente));
        }
        return token;
    }

    public UsuarioLogado verificarToken(String token) {
        tokenPort.verificar(token);

        var tokenBanco = tokenRepository.findTokenByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Token inválido!"));

        if (tokenBanco.getUsuario() != null) {
            return new UsuarioLogado(tokenBanco.getUsuario());
        }
        if (tokenBanco.getGerente() != null) {
            return new UsuarioLogado(tokenBanco.getGerente());
        }
        throw new IllegalArgumentException("Token inválido!");
    }
}