package com.example.cineticket_pro.application.service;

import com.example.cineticket_pro.application.DTOs.*;
import com.example.cineticket_pro.application.port.UsuarioAutenticadoPort;
import com.example.cineticket_pro.domain.entities.*;
import com.example.cineticket_pro.domain.repository.IngressoRepository;
import com.example.cineticket_pro.domain.repository.SessaoRepository;
import com.example.cineticket_pro.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class IngressoService {

    @Autowired
    private IngressoRepository ingressoRepository;

    @Autowired
    private UsuarioAutenticadoPort usuarioAutenticado; // substitui EmpresaContextoPort + SecurityContextHolder

    @Autowired
    private SessaoRepository sessaoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<IngressoResponse> listarTodosIngressosTable() {
        Long empresaId = usuarioAutenticado.empresaId();
        var ingressos = usuarioAutenticado.Cliente()
                ? ingressoRepository.findAllBySessaoFilmeEmpresaIdAndUsuarioId(empresaId, usuarioAutenticado.usuarioLogado().id())
                : ingressoRepository.findAllBySessaoFilmeEmpresaId(empresaId);
        return ingressos.stream().map(IngressoResponse::new).toList();
    }

    @Transactional
    public IngressoResponse salvaringresso(IngressoRequest ingresso, Long id) {
        Long empresaId = usuarioAutenticado.empresaId();

        if (id == 0L) {
            Sessao sessaoBanco = sessaoRepository.findByIdAndFilmeEmpresaId(ingresso.sessaoId(), empresaId)
                    .orElseThrow(() -> new IllegalArgumentException("Sessão não encontrada"));

            if (sessaoBanco.getAssentosDisponiveis() <= 0) {
                throw new IllegalArgumentException("Sessão lotada: não há assentos disponíveis");
            }

            Usuario usuario;
            if (usuarioAutenticado.Cliente()) {
                usuario = usuarioRepository
                        .findByIdAndEmpresaId(usuarioAutenticado.usuarioLogado().id(), empresaId)
                        .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
            } else {
                usuario = usuarioRepository
                        .findByIdAndEmpresaId(ingresso.usuarioId(), empresaId)
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
        }

        Ingresso ingressoBanco = ingressoRepository.findByIdAndSessaoFilmeEmpresaId(id, empresaId).orElse(null);
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

        return null;
    }

    public IngressoResponse buscarPorId(Long id) {
        var ingresso = ingressoRepository
                .findByIdAndSessaoFilmeEmpresaId(id, usuarioAutenticado.empresaId())
                .orElse(null);
        if (ingresso == null) {
            return null;
        }
        return new IngressoResponse(ingresso);
    }

    @Transactional
    public boolean atualizarStatus(EnumStatusIngresso statusIngresso, Long id) {
        Ingresso ingressoBanco = ingressoRepository
                .findByIdAndSessaoFilmeEmpresaId(id, usuarioAutenticado.empresaId())
                .orElse(null);
        if (ingressoBanco != null) {
            ingressoBanco.setStatusIngresso(statusIngresso);
            ingressoRepository.save(ingressoBanco);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean atualizarStatusTipo(EnumTipoIngresso statusIngressoTipo, Long id) {
        Ingresso ingressoBanco = ingressoRepository
                .findByIdAndSessaoFilmeEmpresaId(id, usuarioAutenticado.empresaId())
                .orElse(null);
        if (ingressoBanco != null) {
            ingressoBanco.setStatusTipo(statusIngressoTipo);
            ingressoRepository.save(ingressoBanco);
            return true;
        }
        return false;
    }

    private BigDecimal lerPreco(String preco) {
        try {
            return new BigDecimal(preco.replaceAll("[^0-9,.]", "").replace(",", "."));
        } catch (Exception e) {
            throw new IllegalArgumentException("Preço da sessão inválido");
        }
    }
}