package com.example.cineticket_pro.domain.interfaces;

import com.example.cineticket_pro.infra.configuration.external.dto.ViaCepResponse;

public interface iViaCepExternalRepository {

    ViaCepResponse buscarEnderecoViaCepPorCep(String cep);
}
