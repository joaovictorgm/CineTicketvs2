package com.example.cineticket_pro.domain.entities;

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
public class Filme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    public Long id;
    public String titulo;
    public String duracaoMinutos;
    public String classificacaoEtaria;
    public String dataEstreia;

    public EnumStatusFilme statusFilme = EnumStatusFilme.ATIVO;

}
