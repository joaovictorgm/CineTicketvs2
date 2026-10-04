package com.example.cineticket_pro.application.service;


import com.example.cineticket_pro.application.DTOs.SessaoResponse;
import com.example.cineticket_pro.domain.repository.SessaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SessaoService {

    @Autowired
    private SessaoRepository sessaoRepository;

    public List<SessaoResponse> listarTodasSessoesTable(){
        return  sessaoRepository.findAll().stream().map(SessaoResponse::new).toList();
    }
}
