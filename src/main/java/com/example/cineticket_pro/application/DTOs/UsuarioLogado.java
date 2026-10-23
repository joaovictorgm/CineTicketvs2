package com.example.cineticket_pro.application.DTOs;

import com.example.cineticket_pro.domain.entities.Usuario;
import com.example.cineticket_pro.domain.valueobject.CPF;

public record UsuarioLogado(Long id, String email, CPF cpf,
                            java.util.Collection<? extends org.springframework.security.core.GrantedAuthority> authorities) {

    public UsuarioLogado(Usuario usuario){
        this(
                usuario.getId(),
                usuario.getEmail(),
                usuario.getCpf(),
                usuario.getAuthorities()
        );

    }
}
