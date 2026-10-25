package com.example.cineticket_pro.domain.entities;

import com.example.cineticket_pro.application.DTOs.EnumStatusFilme;
import com.example.cineticket_pro.application.DTOs.FilmeRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Filme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    public Long id;
    public String titulo;
    public String duracaoMinutos;
    public String classificacaoEtaria;
    public String dataEstreia;


    public EnumStatusFilme statusFilme = EnumStatusFilme.ATIVO;

    @ManyToMany(mappedBy = "filmes")
    private Set<Sessao> filmes = new HashSet<>();

    public Filme(FilmeRequest filme) {
        this.setTitulo(filme.titulo());
        this.setDuracaoMinutos(filme.duracaoMinutos());
        this.setClassificacaoEtaria(filme.classificacaoEtaria());
        this.setDataEstreia(filme.dataEstreia());
    }
}
