package com.example.cineticket_pro.application.DTOs;

import com.example.cineticket_pro.domain.entities.EnumStatusGerente;

public record GerenteRequest(
        String nome,
        String email,
        String senha,
        EnumStatusGerente status
) {
}
