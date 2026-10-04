package com.example.cineticket_pro.application.service;


import com.example.cineticket_pro.application.DTOs.GerenteResponse;
import com.example.cineticket_pro.application.DTOs.LoginRequest;
import com.example.cineticket_pro.application.DTOs.LoginResponse;
import com.example.cineticket_pro.application.DTOs.UsuarioResponse;
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
}



