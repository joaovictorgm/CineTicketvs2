package com.example.cineticket_pro.application.DTOs;

import com.example.cineticket_pro.domain.entities.EnumStatusIngresso;
import com.example.cineticket_pro.domain.entities.EnumTipoIngresso;
import com.example.cineticket_pro.domain.entities.Ingresso;

public record IngressoResponse(Long id, String sessao, String filme, String assento, String valorPago, EnumTipoIngresso statusTipo, EnumStatusIngresso statusIngresso, String dataCompra) {

    public IngressoResponse(Ingresso ingressoEntidade){


    this(
            ingressoEntidade.getId(),
            ingressoEntidade.getSessao(),
            ingressoEntidade.getFilme(),
            ingressoEntidade.getAssento(),
            ingressoEntidade.getValorPago(),
            ingressoEntidade.getStatusTipo(),
            ingressoEntidade.getStatusIngresso(),
            ingressoEntidade.getDataCompra()

            );


        }

}