package com.example.cineticket_pro.application.port;

import com.example.cineticket_pro.application.DTOs.UsuarioLogado;

public interface UsuarioAutenticadoPort extends EmpresaContextoPort{
    UsuarioLogado usuarioLogado();

    boolean possuiPapel(String papel);

    default boolean Gerente() { return possuiPapel("ROLE_GERENTE"); }
    default boolean Admin()   { return possuiPapel("ROLE_ADMIN"); }
    default boolean Cliente() { return possuiPapel("ROLE_USER"); }
}
