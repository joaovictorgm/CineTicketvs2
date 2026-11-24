package com.example.cineticket_pro.application.service;

import com.example.cineticket_pro.application.DTOs.*;
import com.example.cineticket_pro.application.port.EmpresaContextoPort;
import com.example.cineticket_pro.application.port.SenhaPort;
import com.example.cineticket_pro.domain.entities.Empresa;
import com.example.cineticket_pro.domain.entities.EnumStatusGerente;
import com.example.cineticket_pro.domain.entities.Gerente;
import com.example.cineticket_pro.domain.interfaces.DadosCnpj;
import com.example.cineticket_pro.domain.interfaces.iCnpjExternalRepository;
import com.example.cineticket_pro.domain.repository.EmpresaRepository;
import com.example.cineticket_pro.domain.repository.GerenteRepository;
import com.example.cineticket_pro.domain.repository.UsuarioRepository;
import com.example.cineticket_pro.domain.valueobject.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GerenteService {

    @Value("${app.admin.secret-key}")
    private String secretAdmin;

    @Autowired
    private GerenteRepository gerenteRepository;

    @Autowired
    private EmpresaContextoPort empresaContexto;

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private iCnpjExternalRepository cnpjExterno;

    @Autowired
    private SenhaPort senhaPort;

    public LoginResponse validarGerenteAutenticadoeRetornaToken(LoginRequest request) {
        String email = new Email(request.email()).toString();

        var gerente = gerenteRepository.findByEmail(email).orElse(null);
        if (gerente != null
                && gerente.getStatus() != EnumStatusGerente.EXCLUIDO
                && senhaPort.conferir(request.senha(), gerente.getSenha())) {

            var token = tokenService.gerarToken(request);
            return new LoginResponse(token);
        }
        return null;
    }

    public List<GerenteResponse> listarTodosGerentesTable() {
        return gerenteRepository.findAllByEmpresaId(empresaContexto.empresaId())
                .stream().map(GerenteResponse::new).toList();
    }

    @Transactional
    public GerenteResponse salvarGerente(GerenteRequest gerente, Long id) {
        Long empresaId = empresaContexto.empresaId();
        String email = new Email(gerente.email()).toString();

        // Novo gerente
        if (id == 0L) {
            if (usuarioRepository.findByEmail(email).isPresent()
                    || gerenteRepository.findByEmail(email).isPresent()) {
                throw new IllegalArgumentException("Email já cadastrado");
            }

            Gerente gerenteBanco = new Gerente(gerente);
            gerenteBanco.setEmail(email);
            gerenteBanco.setSenha(senhaPort.codificar(gerente.senha()));
            gerenteBanco.setStatus(EnumStatusGerente.ATIVO);
            gerenteBanco.setEmpresa(empresaRepository.getReferenceById(empresaId));
            gerenteRepository.save(gerenteBanco);

            return new GerenteResponse(gerenteBanco);
        }

        // Gerente existente (somente da mesma empresa)
        Gerente gerenteBanco = gerenteRepository.findByIdAndEmpresaId(id, empresaId).orElse(null);
        if (gerenteBanco != null) {
            gerenteBanco.setNome(gerente.nome());

            if (!email.equals(gerenteBanco.getEmail())) {
                if (usuarioRepository.findByEmail(email).isPresent()
                        || gerenteRepository.findByEmail(email).isPresent()) {
                    throw new IllegalArgumentException("Email já cadastrado");
                }
                gerenteBanco.setEmail(email);
            }

            if (gerente.senha() != null && !gerente.senha().isBlank()) {
                gerenteBanco.setSenha(senhaPort.codificar(gerente.senha()));
            }

            gerenteRepository.save(gerenteBanco);
            return new GerenteResponse(gerenteBanco);
        }

        return null;
    }

    public GerenteResponse buscarPorId(Long id) {
        var gerente = gerenteRepository
                .findByIdAndEmpresaId(id, empresaContexto.empresaId())
                .orElse(null);
        if (gerente == null) {
            return null;
        }
        return new GerenteResponse(gerente);
    }

    @Transactional
    public boolean atualizarStatus(EnumStatusGerente status, Long id) {
        Gerente gerenteBanco = gerenteRepository
                .findByIdAndEmpresaId(id, empresaContexto.empresaId())
                .orElse(null);
        if (gerenteBanco != null) {
            gerenteBanco.setStatus(status);
            gerenteRepository.save(gerenteBanco);
            return true;
        }
        return false;
    }

    @Transactional
    public CriarAdminResponse criarGerenteeEmpresa(CriarAdminRequest criarGerenteRequest) throws AccessDeniedException {

        if (criarGerenteRequest.secretKey() == null
                || !criarGerenteRequest.secretKey().equals(secretAdmin)) {
            throw new AccessDeniedException("Chave inválida");
        }

        String cnpj = criarGerenteRequest.cnpj() == null
                ? "" : criarGerenteRequest.cnpj().replaceAll("\\D", "");

        if (cnpj.length() != 14) {
            throw new IllegalArgumentException("CNPJ inválido");
        }
        if (empresaRepository.existsByCnpj(cnpj)) {
            throw new IllegalArgumentException("CNPJ já cadastrado");
        }

        String email = new Email(criarGerenteRequest.email()).toString();
        if (usuarioRepository.findByEmail(email).isPresent()
                || gerenteRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Email já cadastrado");
        }

        DadosCnpj dadosCnpj = cnpjExterno.buscarEmpresaPorCnpj(cnpj);

        Empresa empresa = new Empresa();
        empresa.setRazaoSocial(dadosCnpj.razaoSocial());
        empresa.setCnpj(cnpj);
        empresa.setNomeFantasia(criarGerenteRequest.nomeFantasia());
        empresaRepository.save(empresa);

        Gerente gerente = new Gerente(criarGerenteRequest);
        gerente.setEmail(email);
        gerente.setSenha(senhaPort.codificar(criarGerenteRequest.senha()));
        gerente.setEmpresa(empresa);
        gerenteRepository.save(gerente);

        return new CriarAdminResponse(gerente.getId(), "Gerente Salvo com sucesso");
    }
}