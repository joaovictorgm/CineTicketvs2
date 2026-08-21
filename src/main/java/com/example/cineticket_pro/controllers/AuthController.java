package com.example.cineticket_pro.controllers;

import com.example.cineticket_pro.DTOs.LoginRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

    @PostMapping("/login")
    @Operation(description = "Método de login", summary = "Autenticação de Gerentes")
    public ResponseEntity <?> login(@RequestBody LoginRequest loginRequest){
        if(loginRequest.email().equals("string") && loginRequest.senha().equals("string")){
            //Gerar o token
            return ResponseEntity.ok("");
        }
        return ResponseEntity.status(HttpURLConnection.HTTP_UNAUTHORIZED).build();

    }
}
