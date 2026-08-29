package com.example.cineticket_pro.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sessao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String filme;//fk
    public LocalDate data;
    public String sala;
    public EnumTipoExibicao status = EnumTipoExibicao.EXIBIÇÃO_2D;
    public double preco;
    public int assentosDisponiveis;
}
