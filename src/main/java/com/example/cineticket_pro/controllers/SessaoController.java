package com.example.cineticket_pro.controllers;

import com.example.cineticket_pro.entities.Sessao;
import com.example.cineticket_pro.repository.SessaoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sessoes")
@Tag(name="",description = "")
public class SessaoController {

    @Autowired
    private SessaoRepository sessaoRepository;

    @GetMapping
    @Operation(summary = "",description = "")
    public ResponseEntity<?>listarTodos(){
        return ResponseEntity.ok(sessaoRepository.findAll());
    }

    @PostMapping
    @Operation(summary = "",description = "")
    public ResponseEntity<Sessao>criar(@RequestBody Sessao sessao){
        var sessaoBanco = sessaoRepository.save(sessao);
        return ResponseEntity.ok(sessaoBanco);

    }

}
