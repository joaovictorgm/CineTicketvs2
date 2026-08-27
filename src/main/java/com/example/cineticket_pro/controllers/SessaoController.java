package com.example.cineticket_pro.controllers;

import com.example.cineticket_pro.entities.Sessao;
import com.example.cineticket_pro.repository.SessaoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sessoes")
@Tag(name="Sessões",description = "Métodos responsáveis pelo cadastro e consulta das sessões de exibição dos filmes")
public class SessaoController {

    @Autowired
    private SessaoRepository sessaoRepository;

    @GetMapping
    @Operation(summary = "Método de consulta de lista de sessões",description = "Método responsável pela consulta de todas as sessões cadastradas")
    public ResponseEntity<?>listarTodos(){
        return ResponseEntity.ok(sessaoRepository.findAll());
    }

    @PostMapping
    @Operation(summary = "Método de criação de sessões",description = "Método responsável por cadastrar uma nova sessão de exibição")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Sessao>criar(@RequestBody Sessao sessao){
        var sessaoBanco = sessaoRepository.save(sessao);
        return ResponseEntity.ok(sessaoBanco);

    }

}
