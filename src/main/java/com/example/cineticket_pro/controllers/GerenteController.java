package com.example.cineticket_pro.controllers;

import com.example.cineticket_pro.entities.Gerente;
import com.example.cineticket_pro.repository.GerenteRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/gerente")
@Tag(name="",description = "")
public class GerenteController {

    @Autowired
    private GerenteRepository gerenteRepository;

    @GetMapping
    @Operation(summary = "",description = "")
    public ResponseEntity<?>listarTodos(){
        return ResponseEntity.ok(gerenteRepository.findAll());
    }

    @PostMapping
    @Operation(summary = "",description = "")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Gerente>criar(@RequestBody Gerente gerente){
        var gerenteBanco =  gerenteRepository.save(gerente);
        return ResponseEntity.ok(gerenteBanco);
    }
}
