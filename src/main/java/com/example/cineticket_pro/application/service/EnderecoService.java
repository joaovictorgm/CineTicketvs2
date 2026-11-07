package com.example.cineticket_pro.application.service;


import com.example.cineticket_pro.application.DTOs.EnderecoResponse;
import com.example.cineticket_pro.domain.interfaces.iViaCepExternalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class EnderecoService {

    @Autowired
    private iViaCepExternalRepository viaCepExternalRepository;

    public ResponseEntity<EnderecoResponse> buscarEndereco(String cep) {
        var endereCompleto = viaCepExternalRepository.buscarEnderecoViaCepPorCep(cep);

        return  new ResponseEntity.ok(enderrecoService.)
    }
}
