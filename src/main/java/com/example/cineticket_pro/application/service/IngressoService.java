package com.example.cineticket_pro.application.service;


import com.example.cineticket_pro.application.DTOs.*;
import com.example.cineticket_pro.domain.entities.Ingresso;
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
    public IngressoResponse salvaringresso(IngressoRequest ingresso, Long id) {

        //Novo Ingresso
        if(id == 0L) {
            Ingresso ingressoBanco = new Ingresso(ingresso);
            ingressoRepository.save(ingressoBanco);

            return new IngressoResponse(ingressoBanco);
        }else {
            //Ingresso existente
            Ingresso ingressoBanco = ingressoRepository.findById(id).orElse(null);
            if(ingressoBanco!= null ){
                ingressoBanco.setSessao(ingresso.sessao());
                ingressoBanco.setFilme(ingresso.filme());
                ingressoBanco.setAssento(ingresso.assento());
                ingressoBanco.setValorPago(ingresso.valorPago());
                ingressoBanco.setDataCompra(ingresso.dataCompra());
                ingressoRepository.save(ingressoBanco);
                return  new IngressoResponse(ingressoBanco);
            }

        }

        return null;
    }

    public IngressoResponse buscarPorId(Long id) {

        var ingresso = ingressoRepository.findById(id).orElse(null);
        if(ingresso == null){
            return null;
        }

        return new IngressoResponse(ingresso);


    }

    public boolean atualizarStatus(EnumStatusIngresso status, Long id) {

        Ingresso ingressoBanco = ingressoRepository.findById(id).orElse(null);
        if(ingressoBanco!= null ){
            ingressoBanco.setStatusIngresso(status);
            ingressoRepository.save(ingressoBanco);
            return  true;
        }
        return false;

    }


    /*public boolean atualizarStatus(EnumStatusIngresso enumStatusIngresso) {
    }*/

   /* public boolean atualizarStatus(EnumTipoIngresso enumTipoIngresso, Long id) {
    }*/
}
