package com.example.cineticket_pro.application.port;

public interface SenhaPort {
    String codificar(String senhaPura);
    boolean conferir(String senhaPura, String hash);
}