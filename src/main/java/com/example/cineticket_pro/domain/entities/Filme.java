package com.example.cineticket_pro.domain.entities;

import com.example.cineticket_pro.application.DTOs.EnumStatusFilme;
import com.example.cineticket_pro.application.DTOs.FilmeRequest;
import com.example.cineticket_pro.domain.valueobject.ClassificaoEtaria;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Filme  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    public Long id;
    public String titulo;
    public String duracaoMinutos;
    public String classificacaoEtaria;
    public String dataEstreia;


    @ManyToOne
    @JoinColumn(name = "sessao_id", referencedColumnName = "id")
    public Sessao sessao;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "empresa_id", referencedColumnName = "id")
    public Empresa empresa;


    public EnumStatusFilme statusFilme = EnumStatusFilme.ATIVO;



    public Filme(FilmeRequest filme) {
        this.setTitulo(filme.titulo());
        this.setDuracaoMinutos(filme.duracaoMinutos());
        this.setClassificacaoEtaria(filme.classificacaoEtaria());
        this.setDataEstreia(filme.dataEstreia());
    }

    public void setClassificacoaEtaria(String classificacaoEtaria){
        this.classificacaoEtaria = new ClassificaoEtaria(classificacaoEtaria).toString();
    }
}
