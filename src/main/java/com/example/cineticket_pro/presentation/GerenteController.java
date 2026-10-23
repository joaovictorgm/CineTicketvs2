package com.example.cineticket_pro.presentation;

import com.example.cineticket_pro.application.DTOs.AtualizarStatusGerenteRequest;
import com.example.cineticket_pro.application.DTOs.GerenteRequest;
import com.example.cineticket_pro.application.DTOs.GerenteResponse;
import com.example.cineticket_pro.application.service.GerenteService;
import com.example.cineticket_pro.application.DTOs.EnumStatusGerente;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gerentes")
@Tag(name="Gerentes",description = "Métodos responsáveis pelo cadastro e consulta de gerentes do sistema")
public class GerenteController {


    @Autowired
    private GerenteService gerenteService;

    @GetMapping
    @Operation(summary = "Método de consulta de lista de gerentes",description = "Método responsável pela consulta de todos os gerentes cadastrados")
    public ResponseEntity<List<GerenteResponse>>listarTodos(){
        return ResponseEntity.ok(gerenteService.listarTodosGerentesTable());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Método de busca de gerente por ID",description = "Método responsável por buscar um gerente específico através do seu identificador")
    public ResponseEntity<GerenteResponse> buscarPorId(@PathVariable Long id) {
        GerenteResponse gerenteBanco = gerenteService.buscarPorId(id);
        if (gerenteBanco != null) {
            return ResponseEntity.ok(gerenteBanco);
        }
        return ResponseEntity.notFound().build();

    }

@PostMapping
@Operation(summary = "Método de criação de gerentes",description = "Método responsável por cadastrar um novo gerente no sistema")
@ResponseStatus(HttpStatus.CREATED)
public ResponseEntity<?>criar(@RequestBody GerenteRequest gerente){
    try {
        GerenteResponse gerenteBanco = gerenteService.salvarGerente(gerente, 0L);
        return ResponseEntity.ok(gerenteBanco);
    }catch (Exception e){
       return ResponseEntity.badRequest().body(e.getMessage());
    }


}

    @PatchMapping("/{id}/status")
    @Operation(summary = "Método de atualização de status do gerente", description = "Método responsável por atualizar apenas o status de um gerente já cadastrado")
    public ResponseEntity<Void> atualizarStatus(@PathVariable Long id, @RequestBody AtualizarStatusGerenteRequest statusGerenteRequest){
       var atualizarGerente = gerenteService.atualizarStatus(statusGerenteRequest.statusGerente(),id);
        if (atualizarGerente) {
            return ResponseEntity.ok().build();

        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Método de atualização de gerente", description = "Método responsável por atualizar os dados completos de um gerente já cadastrado")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody GerenteRequest gerente){
        try{
           GerenteResponse gerenteBanco = gerenteService.salvarGerente(gerente , id);
            return ResponseEntity.ok(gerenteBanco);
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping("/{id}/excluir")
    @Operation(summary = "Método de exclusão de gerente", description = "Método responsável por desativar (soft delete) um gerente cadastrado")
    public ResponseEntity<Void> excluir(@PathVariable Long id){
        var atualizarGerente = gerenteService.atualizarStatus(EnumStatusGerente.EXCLUIDO, id);
        if(atualizarGerente){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
