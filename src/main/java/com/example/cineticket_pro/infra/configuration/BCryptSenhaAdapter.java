package com.example.cineticket_pro.infra.configuration;

import com.example.cineticket_pro.application.port.SenhaPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BCryptSenhaAdapter implements SenhaPort {

    @Autowired
    private PasswordEncoder passwordEncoder; // bean BCrypt do SecurityConfiguration

    @Override
    public String codificar(String senhaPura) {
        return passwordEncoder.encode(senhaPura);
    }

    @Override
    public boolean conferir(String senhaPura, String hash) {
        return passwordEncoder.matches(senhaPura, hash);
    }
}