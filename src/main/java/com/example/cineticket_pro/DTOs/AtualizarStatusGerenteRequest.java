package com.example.cineticket_pro.DTOs;

import com.example.cineticket_pro.entities.EnumStatusGerente;
import com.example.cineticket_pro.entities.EnumStatusUsuario;

public record AtualizarStatusGerenteRequest(EnumStatusGerente statusGerente) {
}
