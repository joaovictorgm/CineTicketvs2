package com.example.cineticket_pro.application.port;

public interface TokenPort {
    String gerar(String assunto);

    /** Lança IllegalArgumentException se o token for inválido, adulterado ou vencido. */
    void verificar(String token);
}