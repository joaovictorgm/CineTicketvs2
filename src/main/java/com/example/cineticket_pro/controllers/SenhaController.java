package com.example.cineticket_pro.controllers;

import com.example.cineticket_pro.DTOs.EsqueciSenhaRequest;
import com.example.cineticket_pro.entities.Usuario;
import com.example.cineticket_pro.repository.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@Tag(description = "Controler de redefinição de senha", name = "Senha")
@RequestMapping("/senha")
public class SenhaController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/esqueci-senha")
    @Operation(summary = "Método de solicitação de redefinição de senha", description = "Método responsável por gerar um token temporário de redefinição de senha e associá-lo ao usuário correspondente ao email informado")
    public ResponseEntity<?> esqueciSenha(@RequestBody EsqueciSenhaRequest esqueciSenhaRequest){
        var usuarioBanco = usuarioRepository.findByEmail(esqueciSenhaRequest.email());

        if(usuarioBanco.isEmpty()){
            return ResponseEntity.ok("Se o email existir, um link de redefinição foi gerado");
        }
        var usuarioEncontrado = usuarioBanco.get();
        usuarioEncontrado.setResetToken(UUID.randomUUID().toString());// gera um identificador único universal (Universally Unique Identifier) — um valor praticamente impossível de se repetir, mesmo gerado por sistemas diferentes, em momentos diferentes, sem nenhuma coordenação entre eles.
        usuarioEncontrado.setResetTokenExpiracao(LocalDateTime.now().plusMinutes(30));

        usuarioRepository.save(usuarioEncontrado);

        System.out.println("Token de redefinição: "+usuarioEncontrado.getResetToken());

        return ResponseEntity.ok("Se o email existir, um link de redefinição foi gerado");


    }
}
