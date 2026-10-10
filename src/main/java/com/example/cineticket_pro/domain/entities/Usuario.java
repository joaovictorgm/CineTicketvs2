package com.example.cineticket_pro.domain.entities;

import com.example.cineticket_pro.application.DTOs.CriarAdminRequest;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String nome;
    public String telefone;
    public String email;
    public String senha;
    public String filme; //fk
    public String sessao;//fk
    public String ingresso;//fk
    private String role = "ROLE_USER";
    private EnumStatusUsuario status;

    public String resetToken;
    public LocalDateTime resetTokenExpiracao;

    public Usuario(CriarAdminRequest criarAdminRequest) {
        this.setNome(criarAdminRequest.nome());
        this.setEmail(criarAdminRequest.email());
        this.setSenha(criarAdminRequest.senha());

        this.setRole("ROLE_ADMIN");
    }
}

