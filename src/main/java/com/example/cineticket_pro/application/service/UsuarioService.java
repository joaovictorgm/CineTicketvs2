package com.example.cineticket_pro.application.service;


import com.example.cineticket_pro.application.DTOs.LoginRequest;
import com.example.cineticket_pro.application.DTOs.LoginResponse;
import com.example.cineticket_pro.application.DTOs.UsuarioResponse;
import com.example.cineticket_pro.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.HttpURLConnection;
import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired TokenService tokenService;
    public  LoginResponse validarUsuarioAutenticadoeRetornaToken( LoginRequest loginRequest){


        if(usuarioRepository.existsUsuarioByEmailAndSenha(loginRequest.email(),loginRequest.senha())){

            var token = tokenService.gerarToken(String.valueOf(loginRequest));
            return new LoginResponse(token);
        }

        return null;
    }

    public List<UsuarioResponse> listarTodosUsuariosTable(){
        return usuarioRepository.findAll().stream().map(UsuarioResponse::new).toList();
    }
}
