package com.example.cineticket_pro.application.service;


import com.example.cineticket_pro.application.DTOs.*;
import com.example.cineticket_pro.application.port.EmpresaContextoPort;
import com.example.cineticket_pro.domain.entities.Empresa;
import com.example.cineticket_pro.domain.entities.Gerente;
import com.example.cineticket_pro.domain.entities.Usuario;
import com.example.cineticket_pro.domain.repository.EmpresaRepository;
import com.example.cineticket_pro.domain.repository.GerenteRepository;
import com.example.cineticket_pro.domain.repository.UsuarioRepository;
import com.example.cineticket_pro.domain.valueobject.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GerenteService {
    @Value("${spring.secretkey}")
    private String secret;

    @Autowired
    private GerenteRepository gerenteRepository;

     private EmpresaContextoPort empresaContexto;

     @Autowired
     private EmpresaRepository empresaRepository;

    @Autowired TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;


    public LoginResponse validarGerenteAutenticadoeRetornaToken(LoginRequest request) {

        String email = new Email(request.email()).toString();
        if (gerenteRepository.existsGerenteByEmailAndSenha(email, request.senha())) {

            var token = tokenService.gerarToken(request);
            return new LoginResponse(token);
        }

        return null;
    }
public List<GerenteResponse> listarTodosGerentesTable(){
    return gerenteRepository.findAllByEmpresaId(empresaContexto.empresaId()).stream().map(GerenteResponse::new).toList();
}

    public GerenteResponse salvarGerente(GerenteRequest gerente, Long id) {

        //Novo Gerente
        if(id == 0L) {
            Gerente gerenteBanco = new Gerente(gerente);
            gerenteRepository.save(gerenteBanco);

            return new GerenteResponse(gerenteBanco);
        }else {
            //Gerente existente
            Gerente gerenteBanco = gerenteRepository.findById(id).orElse(null);
            if(gerenteBanco!= null ){
                gerenteBanco.setNome(gerente.nome());
                gerenteBanco.setEmail(gerente.email());
                gerenteBanco.setSenha(gerente.senha());
                gerenteRepository.save(gerenteBanco);
                return  new GerenteResponse(gerenteBanco);
            }

        }

        return null;
    }

    public GerenteResponse buscarPorId(Long id) {

        var gerente = gerenteRepository.findById(id).orElse(null);
        if(gerente == null){
            return null;
        }

        return new GerenteResponse(gerente);


    }

    public boolean atualizarStatus(EnumStatusGerente status,Long id) {

        Gerente gerenteBanco = gerenteRepository.findById(id).orElse(null);
        if(gerenteBanco!= null ){
            gerenteBanco.setStatus(status);
            gerenteRepository.save(gerenteBanco);
            return  true;
        }
        return false;

    }

    public CriarAdminResponse criarGerenteeEmpresa(CriarAdminRequest criarGerenteRequest) throws AccessDeniedException {

        if (criarGerenteRequest.secretKey() == null ||!criarGerenteRequest.secretKey().equals(secret)) {
            throw new AccessDeniedException( "Chave inválida");
        }

        if(empresaRepository.existsByCnpj(criarGerenteRequest.cnpj())){
            throw new IllegalArgumentException("CNPJ já cadastrado");
        }
        String email = new Email(criarGerenteRequest.email()).toString();
        if(usuarioRepository.findByEmail(email).isPresent() || gerenteRepository.findByEmail(email).isPresent()){
            throw new IllegalArgumentException("Email já cadastrado");
        }

        Empresa empresa = new Empresa();
        empresa.setRazaoSocial(criarGerenteRequest.razaoSocial());
        empresa.setCnpj(criarGerenteRequest.cnpj());
        empresa.setNomeFantasia(criarGerenteRequest.nomeFantasia());
        empresaRepository.save(empresa);

        Gerente gerente= new Gerente(criarGerenteRequest);
        gerente.setNome(criarGerenteRequest.nome());
        gerente.setEmail(email);
        gerente.setSenha(criarGerenteRequest.senha());
        gerente.setEmpresa(empresa);
        gerenteRepository.save(gerente);


        return new CriarAdminResponse(gerente.getId(), "Gerente Salvo com sucesso");
    }
}



