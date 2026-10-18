package com.example.cineticket_pro.application.DTOs;

public record IngressoRequest(
        String sessao,
        String filme,
        String assento,
        String valorPago,
        EnumTipoIngresso statusTipo,
        EnumStatusIngresso statusIngresso,
        String dataCompra
) {
}
