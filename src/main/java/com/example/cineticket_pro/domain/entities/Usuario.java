package com.example.cineticket_pro.domain.entities;

import com.example.cineticket_pro.application.DTOs.*;
import com.example.cineticket_pro.domain.valueobject.CPF;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    public Long id;
    public String nome;
    public String telefone;
    public String email;
    public String senha;
    public String filme; //fk
    public String sessao;//fk
    public String ingresso;//fk
    private String role = "ROLE_USER";
    @Enumerated(EnumType.STRING)
    private EnumStatusUsuario status;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "empresa_id", referencedColumnName = "id")
    public Empresa empresa;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "tb_codigosessao",
    joinColumns = @JoinColumn(name = "usuario_id"),
    inverseJoinColumns = @JoinColumn(name="sessao_id")
    )

    private Set<Sessao> sessoes = new HashSet<>();

    public void entrarsessao(Sessao sessao){
        this.sessoes.add(sessao);
    }

    public void setEmail(String email) {
        if(!email.isEmpty() && !email.contains("@")){
            throw new IllegalArgumentException("Email inválido");
        }
        this.email = email;
    }

    @JsonIgnore
    public String resetToken;
    @JsonIgnore
    public LocalDateTime resetTokenExpiracao;



    public Usuario(CriarAdminRequest criarAdminRequest) {
        this.setNome(criarAdminRequest.nome());
        this.setEmail(criarAdminRequest.email());
        this.setSenha(criarAdminRequest.senha());
        this.setTelefone(criarAdminRequest.telefone());
        this.setStatus(criarAdminRequest.status() != null ? criarAdminRequest.status() : EnumStatusUsuario.ATIVO);

        this.setRole("ROLE_ADMIN");
    }

    public Usuario(UsuarioRequest usuario) {
        this.setNome(usuario.nome());
        this.setEmail(usuario.email());
        this.setSenha(usuario.senha());
        this.setTelefone(usuario.telefone());
        this.setRole("ROLE_USER");
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




