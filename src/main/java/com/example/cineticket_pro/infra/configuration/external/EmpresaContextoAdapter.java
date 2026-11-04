package com.example.cineticket_pro.infra.configuration.external;

import com.example.cineticket_pro.application.DTOs.UsuarioLogado;
import com.example.cineticket_pro.application.port.EmpresaContextoPort;
import org.springframework.security.core.context.SecurityContextHolder;

public class EmpresaContextoAdapter implements EmpresaContextoPort {


    @Override
    public Long empresaId() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        return ((UsuarioLogado) auth.getPrincipal()).empresa_id();
    }
}
