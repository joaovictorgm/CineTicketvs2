package com.example.cineticket_pro.domain.valueobject;

public class Email {

    private String email;

    public Email(String email) {
        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("E-mail inválido");
        }
        this.email = email.trim().toLowerCase();
    }

    public static boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
        return email.trim().matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }

    @Override
    public String toString() {
        return email;
    }
}


