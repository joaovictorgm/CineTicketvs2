package com.example.cineticket_pro.domain.entities;


import com.example.cineticket_pro.application.DTOs.EnumStatusGerente;
import com.example.cineticket_pro.application.DTOs.GerenteRequest;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Gerente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String nome;

    public String email;

    public String senha;

    private EnumStatusGerente status;

    public Gerente(GerenteRequest gerente) {
        this.setNome(gerente.nome());
        this.setEmail(gerente.email());
        this.setSenha(gerente.senha());
    }
}
