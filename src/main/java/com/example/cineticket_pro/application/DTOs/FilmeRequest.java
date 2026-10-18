package com.example.cineticket_pro.application.DTOs;

public record FilmeRequest(
        String titulo,
        String duracaoMinutos,
        String classificacaoEtaria,
        String dataEstreia,
        EnumStatusFilme status
) {
}
