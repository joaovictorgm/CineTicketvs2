package com.example.cineticket_pro.application.DTOs;

import com.example.cineticket_pro.domain.entities.EnumStatusGerente;
import com.example.cineticket_pro.domain.entities.Gerente;

public record GerenteResponse(Long id, String nome, String email, String senha, EnumStatusGerente status) {

    public GerenteResponse(Gerente gerenteEntidade){
        this(
                gerenteEntidade.getId(),
                gerenteEntidade.getNome(),
                gerenteEntidade.getEmail(),
                gerenteEntidade.getSenha(),
                gerenteEntidade.getStatus()

        );
    }
}
