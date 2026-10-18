package com.example.cineticket_pro.application.service;


import com.example.cineticket_pro.application.DTOs.*;
import com.example.cineticket_pro.domain.entities.Gerente;
import com.example.cineticket_pro.domain.repository.GerenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GerenteService {

    @Autowired
    private GerenteRepository gerenteRepository;

    @Autowired TokenService tokenService;
    /*public LoginResponse validarGerenteAutenticadoRetornaToken(LoginRequest request){

        if(gerenteRepository.existsGerenteByEmailAndSenha(request.email().request.senha()){

            var token = tokenService.gerarToken(String.valueOf(request));
            return new LoginResponse(token);
        }
        return  null;
    }*/
public List<GerenteResponse> listarTodosGerentesTable(){
    return gerenteRepository.findAll().stream().map(GerenteResponse::new).toList();
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
}



