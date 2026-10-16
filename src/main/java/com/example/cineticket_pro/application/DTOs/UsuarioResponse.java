package com.example.cineticket_pro.application.DTOs;

import com.example.cineticket_pro.domain.entities.EnumStatusUsuario;
import com.example.cineticket_pro.domain.entities.Usuario;

public record UsuarioResponse(Long id, String nome, String telefone, String email, String filme, String sessao, String ingresso,
                              String usuarioEntidadeIngresso, EnumStatusUsuario statusUsuario) {

    public UsuarioResponse(Usuario usuarioEntidade){
        this (
                usuarioEntidade.getId(),
                usuarioEntidade.getNome(),
                usuarioEntidade.getCpf().toString(),
                usuarioEntidade.getTelefone(),
                usuarioEntidade.getEmail(),
                usuarioEntidade.getFilme(),
                usuarioEntidade.getSessao(),
                usuarioEntidade.getIngresso(),
                usuarioEntidade.getStatus()
        );
    }
}
