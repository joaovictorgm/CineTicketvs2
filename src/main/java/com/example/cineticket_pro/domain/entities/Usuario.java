package com.example.cineticket_pro.domain.entities;

import com.example.cineticket_pro.application.DTOs.CriarAdminRequest;
import com.example.cineticket_pro.domain.valueobject.CPF;
import jakarta.persistence.*;
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
    @Embedded
    private CPF cpf;
    public String telefone;
    public String email;
    public String senha;
    public String filme; //fk
    public String sessao;//fk
    public String ingresso;//fk
    private String role = "ROLE_USER";
    @Enumerated(EnumType.STRING)
    private EnumStatusUsuario status;

    public String resetToken;
    public LocalDateTime resetTokenExpiracao;

    public Usuario(CriarAdminRequest criarAdminRequest) {
        this.setCpf(new CPF());
        this.setNome(criarAdminRequest.nome());
        this.setEmail(criarAdminRequest.email());
        this.setSenha(criarAdminRequest.senha());
        this.setTelefone(criarAdminRequest.telefone());
        this.setStatus(criarAdminRequest.status() != null ? criarAdminRequest.status() : EnumStatusUsuario.ATIVO);

        this.setRole("ROLE_ADMIN");
    }
}

