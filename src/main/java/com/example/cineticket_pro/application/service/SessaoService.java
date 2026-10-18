package com.example.cineticket_pro.application.service;


import com.example.cineticket_pro.application.DTOs.*;
import com.example.cineticket_pro.domain.entities.Sessao;
import com.example.cineticket_pro.domain.entities.Usuario;
import com.example.cineticket_pro.domain.repository.SessaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SessaoService {

    @Autowired
    private SessaoRepository sessaoRepository;

    public List<SessaoResponse> listarTodasSessoesTable() {
        return sessaoRepository.findAll().stream().map(SessaoResponse::new).toList();
    }

    public SessaoResponse salvarSessao(SessaoRequest sessao, Long id) {

        //Nova Sessao
        if (id == 0L) {
            Sessao sessaoBanco = new Sessao(sessao);
            sessaoRepository.save(sessaoBanco);

            return new SessaoResponse(sessaoBanco);
        } else {
            //Sessao existente
            Sessao sessaoBanco = sessaoRepository.findById(id).orElse(null);
            if (sessaoBanco != null) {
                sessaoBanco.setFilme(sessao.filme());
                sessaoBanco.setData(sessao.data());
                sessaoBanco.setSala(sessao.sala());
                sessaoBanco.setStatus(sessao.status());
                sessaoBanco.setStatusSessao(sessao.statusSessao());
                sessaoBanco.setPreco(sessao.preco());
                sessaoBanco.setAssentosDisponiveis(sessao.assentosDisponiveis());
                sessaoRepository.save(sessaoBanco);
                return new SessaoResponse(sessaoBanco);
            }

        }

        return null;
    }

    public SessaoResponse buscarPorId(Long id) {

        var sessao = sessaoRepository.findById(id).orElse(null);
        if (sessao == null) {
            return null;
        }

        return new SessaoResponse(sessao);


    }

    public boolean atualizarTipoSessao(EnumTipoExibicao status, Long id) {

        Sessao sessaoBanco = sessaoRepository.findById(id).orElse(null);
        if (sessaoBanco != null) {
            sessaoBanco.setStatus(status);
            sessaoRepository.save(sessaoBanco);
            return true;
        }
        return false;
    }

    public boolean atualizarStatusSessao(EnumStatusSessao status, Long id) {

        Sessao sessaoBanco = sessaoRepository.findById(id).orElse(null);
        if (sessaoBanco != null) {
            sessaoBanco.setStatusSessao(status);
            sessaoRepository.save(sessaoBanco);
            return true;
        }
        return false;
    }
}
