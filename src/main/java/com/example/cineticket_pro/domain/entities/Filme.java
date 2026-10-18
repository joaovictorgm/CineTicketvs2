package com.example.cineticket_pro.domain.entities;

import com.example.cineticket_pro.application.DTOs.EnumStatusFilme;
import com.example.cineticket_pro.application.DTOs.FilmeRequest;
import jakarta.persistence.*;
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

    public Filme(FilmeRequest filme) {
        this.setTitulo(filme.titulo());
        this.setDuracaoMinutos(filme.duracaoMinutos());
        this.setClassificacaoEtaria(filme.classificacaoEtaria());
        this.setDataEstreia(filme.dataEstreia());
    }
}
