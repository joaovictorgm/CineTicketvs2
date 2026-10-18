package com.example.cineticket_pro.application.DTOs;

public record SessaoRequest(
        String filme,
        String data,
        String sala,
        EnumTipoExibicao status,
        EnumStatusSessao statusSessao,
        String preco,
        int assentosDisponiveis
) {
}
