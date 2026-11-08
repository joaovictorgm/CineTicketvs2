package com.example.cineticket_pro.application.DTOs;

public record CriarAdminRequest(
        String nome,
        String email,
        String senha,
        String telefone,
        EnumStatusUsuario status,
        EnumStatusGerente statusg,
        String secretKey,
        String razaoSocial,
        String cnpj,
        String nomeFantasia) {
}
