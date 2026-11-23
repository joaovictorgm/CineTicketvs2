package com.example.cineticket_pro.application.service;


import com.example.cineticket_pro.application.DTOs.*;
import com.example.cineticket_pro.application.port.EmpresaContextoPort;
import com.example.cineticket_pro.domain.entities.EnumStatusFilme;
import com.example.cineticket_pro.domain.entities.Filme;
import com.example.cineticket_pro.domain.repository.EmpresaRepository;
import com.example.cineticket_pro.domain.repository.FilmeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilmeService {

    @Autowired
    private FilmeRepository filmeRepository;

    @Autowired
    EmpresaRepository empresaRepository;

    @Autowired
     private EmpresaContextoPort empresaContexto;

    public List<FilmeResponse> listarTodosFilmesTable(){
        return filmeRepository.findAllByEmpresa_id(empresaContexto.empresaId()).stream().map(FilmeResponse::new).toList();
    }

    public FilmeResponse salvarFilme(FilmeRequest filme, Long id) {
        Long empresaId = empresaContexto.empresaId();
        //Novo Filme
        if(id == 0L) {
            Filme filmeBanco = new Filme(filme);
            filmeBanco.setEmpresa(empresaRepository.getReferenceById(empresaId));
            filmeRepository.save(filmeBanco);
            return new FilmeResponse(filmeBanco);
        }else {
            //Filme existente
            Filme filmeBanco = filmeRepository.findByIdAndEmpresaId(id, empresaId).orElse(null);
            if(filmeBanco!= null ){
                filmeBanco.setTitulo(filme.titulo());
                filmeBanco.setDuracaoMinutos(filme.duracaoMinutos());
                filmeBanco.setClassificacaoEtaria(filme.classificacaoEtaria());
                filmeBanco.setDataEstreia(filme.dataEstreia());
                filmeRepository.save(filmeBanco);
                return  new FilmeResponse(filmeBanco);
            }

        }

        return null;
    }

    public FilmeResponse buscarPorId(Long id) {

        var filme = filmeRepository.findByIdAndEmpresaId(id, empresaContexto.empresaId()).orElse(null);
        if(filme == null){
            return null;
        }

        return new FilmeResponse(filme);


    }

    public boolean atualizarStatus(EnumStatusFilme status, Long id) {

        Filme filmeBanco = filmeRepository.findByIdAndEmpresaId(id, empresaContexto.empresaId()).orElse(null);
        if(filmeBanco!= null ){
            filmeBanco.setStatusFilme(status);
            filmeRepository.save(filmeBanco);
            return  true;
        }
        return false;

    }


}
