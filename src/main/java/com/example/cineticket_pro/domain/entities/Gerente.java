package com.example.cineticket_pro.domain.entities;


import com.example.cineticket_pro.application.DTOs.CriarAdminRequest;
import com.example.cineticket_pro.application.DTOs.EnumStatusGerente;
import com.example.cineticket_pro.application.DTOs.EnumStatusUsuario;
import com.example.cineticket_pro.application.DTOs.GerenteRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Gerente implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String nome;

    @Column(unique = true)
    public String email;

    public String senha;

    private String role = "ROLE_GERENTE";


    private EnumStatusGerente status;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "empresa_id", referencedColumnName = "id")
    public Empresa empresa;


    public Gerente(CriarAdminRequest criarGerenteAdminRequest){
        this.setNome(criarGerenteAdminRequest.nome());
        this.setEmail(criarGerenteAdminRequest.email());
        this.setSenha(criarGerenteAdminRequest.senha());
        this.setStatus(criarGerenteAdminRequest.status() != null ? criarGerenteAdminRequest.statusg(): EnumStatusGerente.ATIVO)
        ;
    }


    public Gerente(GerenteRequest gerente) {
        this.setNome(gerente.nome());
        this.setEmail(gerente.email());
        this.setSenha(gerente.senha());
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
