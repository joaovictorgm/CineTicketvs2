package com.example.cineticket_pro.presentation;

import com.example.cineticket_pro.application.DTOs.FilmeRequest;
import com.example.cineticket_pro.application.DTOs.FilmeResponse;
import com.example.cineticket_pro.application.service.FilmeService;
import com.example.cineticket_pro.application.DTOs.EnumStatusFilme;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/filmes")
@Tag(name = "Filmes", description = " responsavel por controlar a criação e consulta de filmes do sistema!")
public class FilmeController {



    @Autowired
    private FilmeService filmeService;

    @GetMapping
    @Operation(summary = "Método de consulta de lista de filmes!", description = "Método responsavel a consulta de todos os filmes sem filtro!")
    public ResponseEntity<List<FilmeResponse>> listarTodos(){

        return ResponseEntity.ok(filmeService.listarTodosFilmesTable());
    }

    @GetMapping("{id}")
    @Operation(summary = "Método de busca de filme por ID", description = "Método responsável por buscar um filme específico através do seu identificador")
    public ResponseEntity<FilmeResponse>buscaPorId(@PathVariable Long id){
        FilmeResponse filmeBanco = filmeService.buscarPorId(id);
        if(filmeBanco != null){
            return ResponseEntity.ok(filmeBanco);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Método de criação de filmes!", description = "Método responsavel de criar filmes sem filtro!")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?>criar(@RequestBody FilmeRequest filme){
        try{
            FilmeResponse filmeBanco = filmeService.salvarFilme(filme, 0L);
            return ResponseEntity.ok(filmeBanco);
        } catch (Exception e){
           return ResponseEntity.badRequest().body(e.getMessage());
        }


    }

    @PutMapping("/{id}")
    @Operation(summary = "Método de atualização de filme", description = "Método responsável por atualizar os dados de um filme já cadastrado")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody FilmeRequest filme){
        try {
           FilmeResponse filmeBanco = filmeService.salvarFilme(filme, id);
           return ResponseEntity.ok(filmeBanco);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
@DeleteMapping("/{id}")
@Operation(summary = "Método de exclusão de filme", description = "Método responsável por remover (ou desativar) um filme cadastrado")
    public ResponseEntity<Void> excluir(@PathVariable Long id){
       var atualizarFilme = filmeService.atualizarStatus(EnumStatusFilme.DESATIVADO,id);

       if(atualizarFilme){
           return ResponseEntity.ok().build();

    }
        return ResponseEntity.notFound().build();
    }


}
