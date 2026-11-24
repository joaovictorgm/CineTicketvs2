package com.example.cineticket_pro.infra.configuration;

import com.example.cineticket_pro.application.DTOs.UsuarioLogado;
import com.example.cineticket_pro.application.port.UsuarioAutenticadoPort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityContextUsuarioAdapter implements UsuarioAutenticadoPort {

    @Override
    public UsuarioLogado usuarioLogado() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof UsuarioLogado logado)) {
            throw new AccessDeniedException("Usuário não autenticado");
        }
        return logado;
    }

    @Override
    public Long empresaId() {
        return usuarioLogado().empresa_id();
    }

    @Override
    public boolean possuiPapel(String papel) {
        return usuarioLogado().authorities().stream()
                .anyMatch(a -> a.getAuthority().equals(papel));
    }
}