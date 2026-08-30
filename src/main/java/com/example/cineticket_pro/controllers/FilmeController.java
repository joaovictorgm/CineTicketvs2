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

    @GetMapping("{id}")
    public ResponseEntity<Filme>buscaPorId(@PathVariable Long id){
        Filme filmeBanco = filmeRepository.findById(id).orElse(null);
        if(filmeBanco != null){
            return ResponseEntity.ok(filmeBanco);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Método de criação de filmes!", description = "Método responsavel de criar filmes sem filtro!")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Filme>criar(@RequestBody Filme filme){
        var filmeBanco = filmeRepository.save(filme);
        return ResponseEntity.ok(filmeBanco);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Filme> atualizar(@PathVariable Long id, @RequestBody Filme filme){
        try {
            Filme filmeBanco = filmeRepository.findById(id).orElse(null);
            if(filmeBanco != null){
                filmeBanco.setTitulo(filme.getTitulo());
                filmeBanco.setDataEstreia(filme.getDataEstreia());
                filmeBanco.setClassificacaoEtaria(filme.getClassificacaoEtaria());
                filmeBanco.setDuracaoMinutos(filme.getDuracaoMinutos());
                filmeRepository.save(filmeBanco);
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.notFound().build();
        } catch (RuntimeException e){
            throw new RuntimeException();
        }
    }

    public ResponseEntity<Void> excluir(@PathVariable Long id){
        Filme filmeBanco = filmeRepository.findById(id).orElse(null);
        if(filmeBanco != null){
            //filmeBanco.setStatus(EnumStatusFilme.DESATIVADO);
            filmeRepository.save(filmeBanco);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }


}
