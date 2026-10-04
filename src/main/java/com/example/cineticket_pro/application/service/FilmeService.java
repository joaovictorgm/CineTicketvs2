package com.example.cineticket_pro.application.service;


import com.example.cineticket_pro.application.DTOs.FilmeResponse;
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
}
