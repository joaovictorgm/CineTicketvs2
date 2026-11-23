package com.example.cineticket_pro.application.DTOs;

import com.example.cineticket_pro.domain.entities.EnumStatusFilme;

public record FilmeRequest(
        String titulo,
        String duracaoMinutos,
        String classificacaoEtaria,
        String dataEstreia,
        EnumStatusFilme status
) {
}
