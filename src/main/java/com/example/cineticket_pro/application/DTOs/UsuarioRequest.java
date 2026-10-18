package com.example.cineticket_pro.application.DTOs;

public record UsuarioRequest(
                             String nome,
                             String email,
                             String cpf,
                             String senha,
                             String telefone,
                             EnumStatusUsuario status
                            ) {
}
