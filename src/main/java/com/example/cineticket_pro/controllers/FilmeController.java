package com.example.cineticket_pro.controllers;

import com.example.cineticket_pro.entities.Filme;
import com.example.cineticket_pro.repository.FilmeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/filmes")
public class FilmeController {

    @Autowired
    private FilmeRepository filmeRepository;

    @GetMapping
    public ResponseEntity<?> listarTodos(){

        return ResponseEntity.ok(filmeRepository.findAll());
    }

    public ResponseEntity<Filme> criar(@RequestBody Filme filme){
        var filmeBanco = filmeRepository.save(filme);
        return ResponseEntity.ok(filmeBanco);
    }
}
