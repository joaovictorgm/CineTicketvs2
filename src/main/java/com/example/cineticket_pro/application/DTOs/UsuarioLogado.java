package com.example.cineticket_pro.application.DTOs;

import com.example.cineticket_pro.domain.entities.Gerente;
import com.example.cineticket_pro.domain.entities.Usuario;


public record UsuarioLogado(Long id,  String email,Long empresa_id,
                            java.util.Collection<? extends org.springframework.security.core.GrantedAuthority> authorities) {

    public UsuarioLogado(Usuario usuario){
        this(
                usuario.getId(),
                usuario.getEmail(),
                usuario.getEmpresa()!=null? usuario.getEmpresa().getId():null,
                usuario.getAuthorities()
        );


        }

    public UsuarioLogado(Gerente gerente){
        this(
                gerente.getId(),
                gerente.getEmail(),
                gerente.getEmpresa()!=null? gerente.getEmpresa().getId():null,
                gerente.getAuthorities()
        );


    }



    }

