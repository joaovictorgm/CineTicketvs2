package com.example.cineticket_pro.controllers;

import com.example.cineticket_pro.entities.Filme;
import com.example.cineticket_pro.repository.FilmeRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/filmes")
@Tag(name = "Filmes", description = " responsavel por controlar a criação e consulta de filmes do sistema!")
public class FilmeController {

    @Autowired
    private FilmeRepository filmeRepository;

    @GetMapping
    @Operation(summary = "Método de consulta de lista de filmes!", description = "Método responsavel a consulta de todos os filmes sem filtro!")
    public ResponseEntity<?> listarTodos(){

        return ResponseEntity.ok(filmeRepository.findAll());
    }

    @PostMapping
    @Operation(summary = "Método de criação de filmes!", description = "Método responsavel de criar filmes sem filtro!")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Filme>criar(@RequestBody Filme filme){
        var filmeBanco = filmeRepository.save(filme);
        return ResponseEntity.ok(filmeBanco);
    }


}
