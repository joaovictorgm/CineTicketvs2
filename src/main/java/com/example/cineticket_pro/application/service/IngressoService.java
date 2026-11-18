package com.example.cineticket_pro.application.service;


import com.example.cineticket_pro.application.DTOs.*;
import com.example.cineticket_pro.application.port.EmpresaContextoPort;
import com.example.cineticket_pro.domain.entities.Ingresso;
import com.example.cineticket_pro.domain.entities.Sessao;
import com.example.cineticket_pro.domain.repository.IngressoRepository;
import com.example.cineticket_pro.domain.repository.SessaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngressoService {

    @Autowired
    private IngressoRepository ingressoRepository;

    @Autowired
    private EmpresaContextoPort empresaContexto;

    @Autowired
    private SessaoRepository sessaoRepository;

    public List<IngressoResponse> listarTodosIngressosTable(){
        return ingressoRepository.findAllBySessaoFilmeEmpresaId(empresaContexto.empresaId()).stream().map(IngressoResponse::new).toList();

    }

    public IngressoResponse salvaringresso(IngressoRequest ingresso, Long id) {

        //Novo Ingresso
        if (id == 0L) {
            Sessao sessaoBanco = sessaoRepository.findByIdAndFilmeEmpresaId(ingresso.sessaoId(), empresaContexto.empresaId()).orElseThrow(() -> new IllegalArgumentException("Sessão não encontrada"));
            Ingresso ingressoBanco = new Ingresso(ingresso);
            ingressoBanco.setSessao(sessaoBanco);
            ingressoBanco.setFilme(sessaoBanco.getFilme());
            ingressoRepository.save(ingressoBanco);

            return new IngressoResponse(ingressoBanco);
        } else {
            //Ingresso existente
            Ingresso ingressoBanco = ingressoRepository.findByIdAndSessaoFilmeEmpresaId(id , empresaContexto.empresaId()).orElse(null);
            if (ingressoBanco != null) {
                ingressoBanco.setAssento(ingresso.assento());
                ingressoBanco.setValorPago(ingresso.valorPago());
                ingressoBanco.setDataCompra(ingresso.dataCompra());
                ingressoRepository.save(ingressoBanco);
                return new IngressoResponse(ingressoBanco);
            }

        }

        return null;
    }

    public IngressoResponse buscarPorId(Long id) {

        var ingresso = ingressoRepository.findByIdAndSessaoFilmeEmpresaId(id, empresaContexto.empresaId()).orElse(null);
        if (ingresso == null) {
            return null;
        }

        return new IngressoResponse(ingresso);


    }

    public boolean atualizarStatus(EnumStatusIngresso statusIngresso, Long id) {

        Ingresso ingressoBanco = ingressoRepository.findByIdAndSessaoFilmeEmpresaId(id, empresaContexto.empresaId()).orElse(null);
        if (ingressoBanco != null) {
            ingressoBanco.setStatusIngresso(statusIngresso);
            ingressoRepository.save(ingressoBanco);
            return true;
        }
        return false;

    }


    public boolean atualizarStatusTipo(EnumTipoIngresso statusIngressoTipo, Long id) {

        Ingresso ingressoBancoTipo = ingressoRepository.findByIdAndSessaoFilmeEmpresaId(id, empresaContexto.empresaId()).orElse(null);
        if (ingressoBancoTipo != null){
            ingressoBancoTipo.setStatusTipo(statusIngressoTipo);
            ingressoRepository.save(ingressoBancoTipo);
            return true;
        }
        return false;
    }
}



