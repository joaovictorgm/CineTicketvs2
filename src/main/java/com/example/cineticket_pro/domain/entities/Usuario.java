package com.example.cineticket_pro.domain.entities;

import com.example.cineticket_pro.application.DTOs.*;
import com.example.cineticket_pro.domain.valueobject.CPF;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Usuario implements UserDetails {
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
    public Usuario(UsuarioRequest usuario) {
        this.setCpf(new CPF(usuario.cpf()));
        this.setNome(usuario.nome());
        this.setEmail(usuario.email());
        this.setSenha(usuario.senha());
        this.setTelefone(usuario.telefone());
        this.setRole("ROLE_USER");
    }

    public UsuarioLogado getUsuarioLogado(){
        return (UsuarioLogado) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.role));
    }

    @Override
    public @Nullable String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return "";
    }
}

