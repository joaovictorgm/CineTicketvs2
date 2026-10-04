package com.example.cineticket_pro.application.DTOs;

import com.example.cineticket_pro.domain.entities.EnumStatusFilme;
import com.example.cineticket_pro.domain.entities.Filme;

public record FilmeResponse (Long id, String titulo, String duracaoMinutos, String classificaoEtaria, String dataEstreia, EnumStatusFilme statusFilme){

    public FilmeResponse(Filme filmeEntidade){
        this(

                filmeEntidade.getId(),
                filmeEntidade.getTitulo(),
                filmeEntidade.getDuracaoMinutos(),
                filmeEntidade.getClassificacaoEtaria(),
                filmeEntidade.getDataEstreia(),
                filmeEntidade.getStatusFilme()
        );
    }
}
