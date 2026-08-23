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
@Tag(name="",description = "")
public class IngressoController {

    @Autowired
    private IngressoRepository ingressoRepository;

    @GetMapping
    @Operation(summary = "", description = "")
    public ResponseEntity<?> listarTodos(){
        return ResponseEntity.ok(ingressoRepository.findAll());
    }

    @PostMapping
    @Operation(summary = "",description = "")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Ingresso>criar(@RequestBody Ingresso ingresso){
        var ingressoBanco = ingressoRepository.save(ingresso);
        return ResponseEntity.ok(ingresso);
    }
}
