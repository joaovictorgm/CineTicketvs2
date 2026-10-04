package com.example.cineticket_pro.application.service;


import com.example.cineticket_pro.application.DTOs.IngressoResponse;
import com.example.cineticket_pro.domain.repository.IngressoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngressoService {

    @Autowired
    private IngressoRepository ingressoRepository;

    public List<IngressoResponse> listarTodosIngressosTable(){
        return ingressoRepository.findAll().stream().map(IngressoResponse::new).toList();

    }
}
