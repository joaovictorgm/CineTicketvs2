package com.example.cineticket_pro.presentation;

import com.example.cineticket_pro.application.DTOs.LoginRequest;
import com.example.cineticket_pro.application.DTOs.LoginResponse;

import com.example.cineticket_pro.application.service.TokenService;
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

    @PostMapping("/login")
    @Operation(description = "Método de login", summary = "Autenticação de Gerentes")
    public ResponseEntity <?> login(@RequestBody LoginRequest loginRequest){

        var resultadoAutenticacaoRetornoToken = usuarioService.validarUsuarioAutenticadoeRetornaToken(loginRequest);

        if(resultadoAutenticacaoRetornoToken != null){


            return ResponseEntity.ok(loginRequest);
        }
        return ResponseEntity.status(HttpURLConnection.HTTP_UNAUTHORIZED).build();

    }
}
