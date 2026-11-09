package com.example.cineticket_pro.presentation;

import com.example.cineticket_pro.application.DTOs.LoginRequest;

import com.example.cineticket_pro.application.service.GerenteService;
import com.example.cineticket_pro.application.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.HttpURLConnection;

@RestController
@Tag(description = "Controler de autenticação", name = "Autenticação")
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private GerenteService gerenteService;

    @PostMapping("/login")
    @Operation(description = "Método de login", summary = "Autenticação de Usuário")
    public ResponseEntity <?> login(@RequestBody LoginRequest loginRequest){

        var resultadoAutenticacaoRetornoTokenUsuario = usuarioService.validarUsuarioAutenticadoeRetornaToken(loginRequest);
        var resultadoAutenticacaoRetornoTokenGerente = gerenteService.validarGerenteAutenticadoeRetornaToken(loginRequest);

        if(resultadoAutenticacaoRetornoTokenUsuario != null ){

            return ResponseEntity.ok((resultadoAutenticacaoRetornoTokenUsuario ));
        }if(resultadoAutenticacaoRetornoTokenGerente != null) {
            return ResponseEntity.ok((resultadoAutenticacaoRetornoTokenGerente ));
        }
        return ResponseEntity.status(HttpURLConnection.HTTP_UNAUTHORIZED).build();

    }


}
