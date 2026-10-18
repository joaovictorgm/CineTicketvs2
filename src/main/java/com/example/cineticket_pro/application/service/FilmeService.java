package com.example.cineticket_pro.application.service;


import com.example.cineticket_pro.application.DTOs.*;
import com.example.cineticket_pro.domain.entities.Filme;
import com.example.cineticket_pro.domain.entities.Usuario;
import com.example.cineticket_pro.domain.repository.FilmeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilmeService {

    @Autowired
    private FilmeRepository filmeRepository;

    public List<FilmeResponse> listarTodosFilmesTable(){
        return filmeRepository.findAll().stream().map(FilmeResponse::new).toList();
    }

    public FilmeResponse salvarFilme(FilmeRequest filme, Long id) {

        //Novo Filme
        if(id == 0L) {
            Filme filmeBanco = new Filme(filme);
            filmeRepository.save(filmeBanco);

            return new FilmeResponse(filmeBanco);
        }else {
            //Filme existente
            Filme filmeBanco = filmeRepository.findById(id).orElse(null);
            if(filmeBanco!= null ){
                filmeBanco.setTitulo(filme.titulo());
                filmeBanco.setDuracaoMinutos(filme.duracaoMinutos());
                filmeBanco.setClassificacaoEtaria(filme.classificacaoEtaria());
                filmeBanco.setDataEstreia(filme.dataEstreia());
                return  new FilmeResponse(filmeBanco);
            }

        }

        return null;
    }

    public FilmeResponse buscarPorId(Long id) {

        var filme = filmeRepository.findById(id).orElse(null);
        if(filme == null){
            return null;
        }

        return new FilmeResponse(filme);


    }

    public boolean atualizarStatus(EnumStatusFilme status, Long id) {

        Filme filmeBanco = filmeRepository.findById(id).orElse(null);
        if(filmeBanco!= null ){
            filmeBanco.setStatusFilme(status);
            filmeRepository.save(filmeBanco);
            return  true;
        }
        return false;

    }


}
