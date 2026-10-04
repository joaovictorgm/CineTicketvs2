package com.example.cineticket_pro.application.DTOs;

import com.example.cineticket_pro.domain.entities.EnumStatusSessao;
import com.example.cineticket_pro.domain.entities.EnumTipoExibicao;
import com.example.cineticket_pro.domain.entities.Sessao;

public record SessaoResponse(Long id, String filme, String data, String sala, EnumTipoExibicao status, EnumStatusSessao statusSessao, String preco, int assentosDisponiveis) {

    public SessaoResponse(Sessao sessaoEntidade){
        this (
                sessaoEntidade.getId(),
                sessaoEntidade.getFilme(),
                sessaoEntidade.getData(),
                sessaoEntidade.getSala(),
                sessaoEntidade.getStatus(),
                sessaoEntidade.getStatusSessao(),
                sessaoEntidade.getPreco(),
                sessaoEntidade.getAssentosDisponiveis()

        );

    }




}
