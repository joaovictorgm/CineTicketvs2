package com.example.cineticket_pro.application.DTOs;

public record GerenteRequest(
        String nome,
        String email,
        String senha,
        EnumStatusGerente status
) {
}
