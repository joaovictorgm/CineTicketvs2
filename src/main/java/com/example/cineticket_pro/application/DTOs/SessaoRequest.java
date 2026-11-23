package com.example.cineticket_pro.application.DTOs;

import com.example.cineticket_pro.domain.entities.EnumStatusSessao;
import com.example.cineticket_pro.domain.entities.EnumTipoExibicao;

public record SessaoRequest(
        String filme,
        String data,
        String sala,
        EnumTipoExibicao status,
        EnumStatusSessao statusSessao,
        String preco,
        int assentosDisponiveis,
        Long filmeId
) {
}
