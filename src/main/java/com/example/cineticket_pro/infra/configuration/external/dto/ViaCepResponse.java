package com.example.cineticket_pro.infra.configuration.external.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
@JsonIgnore(ignoreUnknown = true)
public class ViaCepResponse {

    private String cep;
    private String logradouro;
    private String bairro;
    private String localidadae;
    private String uf;
    private String estado;
    private Boolean erro;
}
