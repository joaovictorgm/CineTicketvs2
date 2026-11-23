package com.example.cineticket_pro.application.DTOs;

import com.example.cineticket_pro.domain.entities.EnumTipoExibicao;

public record AtualizarSessaoRequest(EnumTipoExibicao statusExibicao) {
}
