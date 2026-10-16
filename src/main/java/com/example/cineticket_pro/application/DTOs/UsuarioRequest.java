package com.example.cineticket_pro.application.DTOs;

import com.example.cineticket_pro.domain.entities.EnumStatusUsuario;

public record UsuarioRequest(
                             String nome,
                             String email,
                             String cpf,
                             String senha,
                             String telefone,
                             EnumStatusUsuario status
                            ) {
}
