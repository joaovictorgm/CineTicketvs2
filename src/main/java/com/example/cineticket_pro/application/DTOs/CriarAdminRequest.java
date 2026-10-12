package com.example.cineticket_pro.application.DTOs;

import com.example.cineticket_pro.domain.entities.EnumStatusUsuario;

public record CriarAdminRequest(
        String nome,
        String email,
        String senha,
        String telefone,
        EnumStatusUsuario status,
        String secretKey) {
}
