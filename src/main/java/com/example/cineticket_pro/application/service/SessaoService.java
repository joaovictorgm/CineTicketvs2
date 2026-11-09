package com.example.cineticket_pro.application.service;


import com.example.cineticket_pro.application.DTOs.*;
import com.example.cineticket_pro.application.port.EmpresaContextoPort;
import com.example.cineticket_pro.domain.entities.Empresa;
import com.example.cineticket_pro.domain.entities.Filme;
import com.example.cineticket_pro.domain.entities.Sessao;
import com.example.cineticket_pro.domain.entities.Usuario;
import com.example.cineticket_pro.domain.repository.EmpresaRepository;
import com.example.cineticket_pro.domain.repository.FilmeRepository;
import com.example.cineticket_pro.domain.repository.SessaoRepository;
import org.hibernate.internal.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SessaoService {

    @Autowired
    private SessaoRepository sessaoRepository;

    @Autowired
    private FilmeRepository filmeRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private EmpresaContextoPort empresaContexto;

    public List<SessaoResponse> listarTodasSessoesTable() {
        return sessaoRepository.findAllByEmpresaId(empresaContexto.empresaId()).stream().map(SessaoResponse::new).toList();
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

    /*public boolean juntarSessaoFilme(Long id, JuntarFilmeSessaoRequest juntarFilmeSessaoRequest){

        Long empresaId = empresaContexto.empresaId();
        Sessao sessaoBanco = sessaoRepository.findByIdAndEmpresaId(id, empresaId).orElse(null);
        Filme filmeBanco = filmeRepository.findByIdAndEmpresaId(juntarFilmeSessaoRequest.filme_id(),empresaId);

        if(sessaoBanco!= null && filmeBanco != null){
            sessaoBanco.setFilmes(filmeBanco);
            sessaoRepository.save(sessaoBanco);
            return true;

            }


        return false;

    }*/
}
