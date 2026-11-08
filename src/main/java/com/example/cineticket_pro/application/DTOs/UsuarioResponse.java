package com.example.cineticket_pro.application.DTOs;

import com.example.cineticket_pro.domain.entities.Usuario;

public record UsuarioResponse(Long id, String nome, String telefone, String email,
                              EnumStatusUsuario statusUsuario) {

    public UsuarioResponse(Usuario usuarioEntidade){
        this (
                usuarioEntidade.getId(),
                usuarioEntidade.getNome(),
                usuarioEntidade.getTelefone(),
                usuarioEntidade.getEmail(),
                usuarioEntidade.getStatus()
        );
    }
}
