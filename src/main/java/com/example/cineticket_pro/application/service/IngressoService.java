package com.example.cineticket_pro.application.service;


import com.example.cineticket_pro.application.DTOs.*;
import com.example.cineticket_pro.application.port.EmpresaContextoPort;
import com.example.cineticket_pro.domain.entities.*;
import com.example.cineticket_pro.domain.repository.IngressoRepository;
import com.example.cineticket_pro.domain.repository.SessaoRepository;
import com.example.cineticket_pro.domain.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class IngressoService {

    @Autowired
    private IngressoRepository ingressoRepository;

    @Autowired
    private EmpresaContextoPort empresaContexto;

    @Autowired
    private SessaoRepository sessaoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<IngressoResponse> listarTodosIngressosTable(){
        Long empresaId = empresaContexto.empresaId();
        var listaUsuarios = cliente() ? ingressoRepository.findAllBySessaoFilmeEmpresaIdAndUsuarioId(empresaId, logado().id())
                : ingressoRepository.findAllBySessaoFilmeEmpresaId(empresaId);
        return listaUsuarios.stream().map(IngressoResponse::new).toList();

    }

    @Transactional
    public IngressoResponse salvaringresso(IngressoRequest ingresso, Long id) {

        if (id == 0L) {
            Sessao sessaoBanco = sessaoRepository.findByIdAndFilmeEmpresaId(ingresso.sessaoId(), empresaContexto.empresaId()).orElseThrow(() -> new IllegalArgumentException("Sessão não encontrada"));

            if (sessaoBanco.getAssentosDisponiveis() <= 0) {
                throw new IllegalArgumentException("Sessão lotada: não há assentos disponíveis");
            }

            Usuario usuario;
            if (cliente()) {
                usuario = usuarioRepository
                        .findByIdAndEmpresaId(logado().id(), empresaContexto.empresaId())
                        .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
            } else {
                usuario = usuarioRepository
                        .findByIdAndEmpresaId(ingresso.usuarioId(), empresaContexto.empresaId())
                        .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));
            }

            BigDecimal valor = lerPreco(sessaoBanco.getPreco());
            EnumTipoIngresso tipoFinal = ingresso.statusTipo();

            if (usuario.getTipoCliente() == EnumTipoCliente.ESTUDANTE) {
                valor = valor.multiply(new BigDecimal("0.50"));
                if (tipoFinal == null) {
                    tipoFinal = EnumTipoIngresso.MEIA;
                }
            }

            Ingresso ingressoBanco = new Ingresso(ingresso);
            ingressoBanco.setSessao(sessaoBanco);
            ingressoBanco.setFilme(sessaoBanco.getFilme());
            ingressoBanco.setUsuario(usuario);
            ingressoBanco.setStatusTipo(tipoFinal != null ? tipoFinal : EnumTipoIngresso.INTEIRA);
            ingressoBanco.setValorPago(valor.setScale(2, RoundingMode.HALF_UP).toString());
            ingressoRepository.save(ingressoBanco);

            sessaoBanco.setAssentosDisponiveis(sessaoBanco.getAssentosDisponiveis() - 1);
            sessaoRepository.save(sessaoBanco);

            return new IngressoResponse(ingressoBanco);
        } else {

            Ingresso ingressoBanco = ingressoRepository.findByIdAndSessaoFilmeEmpresaId(id, empresaContexto.empresaId()).orElse(null);
            if (ingressoBanco != null) {
                ingressoBanco.setAssento(ingresso.assento());
                ingressoBanco.setValorPago(ingresso.valorPago());
                ingressoBanco.setDataCompra(ingresso.dataCompra());
                if (ingresso.statusTipo() != null) {
                    ingressoBanco.setStatusTipo(ingresso.statusTipo());
                }
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
        if (ingressoBancoTipo != null) {
            ingressoBancoTipo.setStatusTipo(statusIngressoTipo);
            ingressoRepository.save(ingressoBancoTipo);
            return true;
        }
        return false;
    }

    private UsuarioLogado logado() {
        return (UsuarioLogado) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    private boolean cliente() {
        return logado().authorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER"));
    }

    private BigDecimal lerPreco(String preco) {
        try {
            return new BigDecimal(preco.replaceAll("[^0-9,.]", "").replace(",", "."));
        } catch (Exception e) {
            throw new IllegalArgumentException("Preço da sessão inválido");
        }
    }
}