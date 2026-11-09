package com.example.cineticket_pro.application.DTOs;

import com.example.cineticket_pro.domain.entities.Ingresso;

public record IngressoResponse(Long id,  String assento, String valorPago, EnumTipoIngresso statusTipo, EnumStatusIngresso statusIngresso, String dataCompra) {

    public IngressoResponse(Ingresso ingressoEntidade){


    this(
            ingressoEntidade.getId(),
            ingressoEntidade.getAssento(),
            ingressoEntidade.getValorPago(),
            ingressoEntidade.getStatusTipo(),
            ingressoEntidade.getStatusIngresso(),
            ingressoEntidade.getDataCompra()

            );


        }

}