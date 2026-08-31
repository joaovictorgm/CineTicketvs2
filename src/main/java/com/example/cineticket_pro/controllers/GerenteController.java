package com.example.cineticket_pro.controllers;

import com.example.cineticket_pro.DTOs.AtualizarStatusGerenteRequest;
import com.example.cineticket_pro.entities.EnumStatusGerente;
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
@Tag(name="Gerentes",description = "Métodos responsáveis pelo cadastro e consulta de gerentes do sistema")
public class GerenteController {

    @Autowired
    private GerenteRepository gerenteRepository;

    @GetMapping
    @Operation(summary = "Método de consulta de lista de gerentes",description = "Método responsável pela consulta de todos os gerentes cadastrados")
    public ResponseEntity<?>listarTodos(){
        return ResponseEntity.ok(gerenteRepository.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Método de busca de gerente por ID",description = "Método responsável por buscar um gerente específico através do seu identificador")
    public ResponseEntity<Gerente> buscarPorId(@PathVariable Long id) {
        Gerente gerenteBanco = gerenteRepository.findById(id).orElse(null);
        if (gerenteBanco != null) {
            return ResponseEntity.ok(gerenteBanco);
        }
        return ResponseEntity.notFound().build();

    }

@PostMapping
@Operation(summary = "Método de criação de gerentes",description = "Método responsável por cadastrar um novo gerente no sistema")
@ResponseStatus(HttpStatus.CREATED)
public ResponseEntity<Gerente>criar(@RequestBody Gerente gerente){
    var gerenteBanco =  gerenteRepository.save(gerente);
    return ResponseEntity.ok(gerenteBanco);
}

    @PatchMapping("/{id}/status")
    @Operation(summary = "Método de atualização de status do gerente", description = "Método responsável por atualizar apenas o status de um gerente já cadastrado")
    public ResponseEntity<Void> atualizarStatus(@PathVariable Long id, @RequestBody AtualizarStatusGerenteRequest statusGerenteRequest){
        Gerente gerenteBanco = gerenteRepository.findById(id).orElse(null);
        if (gerenteBanco != null) {
            gerenteBanco.setStatus(statusGerenteRequest.statusGerente());
            gerenteRepository.save(gerenteBanco);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Método de atualização de gerente", description = "Método responsável por atualizar os dados completos de um gerente já cadastrado")
    public ResponseEntity<Gerente> atualizar(@PathVariable Long id, @RequestBody Gerente gerente){
        try{
            Gerente gerenteBanco = gerenteRepository.findById(id).orElse(null);
            if(gerenteBanco != null){
                gerenteBanco.setStatus(gerente.getStatus());
                gerenteBanco.setNome(gerente.getNome());
                gerenteBanco.setEmail(gerente.getEmail());
                gerenteBanco.setSenha(gerente.getSenha());
                gerenteRepository.save(gerenteBanco);
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.notFound().build();
        } catch (RuntimeException e){
            throw new RuntimeException(e);
        }
    }
    @DeleteMapping("/{id}/excluir")
    @Operation(summary = "Método de exclusão de gerente", description = "Método responsável por desativar (soft delete) um gerente cadastrado")
    public ResponseEntity<Void> excluir(@PathVariable Long id){
        Gerente gerenteBanco = gerenteRepository.findById(id).orElse(null);
        if(gerenteBanco != null){
            gerenteBanco.setStatus(EnumStatusGerente.EXCLUIDO);
            gerenteRepository.save(gerenteBanco);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
