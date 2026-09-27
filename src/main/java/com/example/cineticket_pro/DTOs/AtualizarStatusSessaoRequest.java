package com.example.cineticket_pro.DTOs;

import com.example.cineticket_pro.entities.EnumStatusSessao;
import com.example.cineticket_pro.entities.EnumTipoExibicao;

public record AtualizarStatusSessaoRequest(EnumStatusSessao statusSessao) {
}
