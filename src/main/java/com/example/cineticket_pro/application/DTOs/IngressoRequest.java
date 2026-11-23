package com.example.cineticket_pro.application.DTOs;

import com.example.cineticket_pro.domain.entities.EnumStatusIngresso;
import com.example.cineticket_pro.domain.entities.EnumTipoIngresso;

public record IngressoRequest(
        String sessao,
        String filme,
        String assento,
        String valorPago,
        EnumTipoIngresso statusTipo,
        EnumStatusIngresso statusIngresso,
        String dataCompra,
        Long sessaoId,
        Long usuarioId
) {
}
