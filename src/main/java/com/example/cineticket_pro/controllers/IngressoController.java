package com.example.cineticket_pro.controllers;

import com.example.cineticket_pro.entities.Ingresso;
import com.example.cineticket_pro.repository.IngressoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ingressos")
@Tag(name="Ingressos",description = "Métodos responsáveis pela emissão e consulta de ingressos das sessões")
public class IngressoController {

    @Autowired
    private IngressoRepository ingressoRepository;

    @GetMapping
    @Operation(summary = "Método de consulta de lista de ingressos", description = "Método responsável pela consulta de todos os ingressos emitidos")
    public ResponseEntity<?> listarTodos(){
        return ResponseEntity.ok(ingressoRepository.findAll());
    }

    @PostMapping
    @Operation(summary = "Método de criação de ingressos",description = "Método responsável por registrar a emissão de um novo ingresso")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Ingresso>criar(@RequestBody Ingresso ingresso){
        var ingressoBanco = ingressoRepository.save(ingresso);
        return ResponseEntity.ok(ingressoBanco);
    }
}
