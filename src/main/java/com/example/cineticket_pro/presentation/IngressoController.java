package com.example.cineticket_pro.presentation;

import com.example.cineticket_pro.application.DTOs.*;
import com.example.cineticket_pro.application.service.IngressoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ingressos")
@Tag(name="Ingressos",description = "Métodos responsáveis pela emissão e consulta de ingressos das sessões")
public class IngressoController {



    @Autowired
    private IngressoService ingressoService;

    @GetMapping
    @Operation(summary = "Método de consulta de lista de ingressos", description = "Método responsável pela consulta de todos os ingressos emitidos")
    public ResponseEntity<List<IngressoResponse>> listarTodos() {
        return ResponseEntity.ok(ingressoService.listarTodosIngressosTable());
    }


    @GetMapping("/{id}")
    @Operation(summary = "Método de busca de ingresso por ID", description = "Método responsável por buscar um ingresso específico através do seu identificador")
    public ResponseEntity<IngressoResponse> buscarPorId(@PathVariable Long id) {
        IngressoResponse ingressoBanco = ingressoService.buscarPorId(id);
        if (ingressoBanco != null) {
            return ResponseEntity.ok(ingressoBanco);
        }
        return ResponseEntity.notFound().build();

    }

    @PostMapping
    @Operation(summary = "Método de criação de ingressos", description = "Método responsável por registrar a emissão de um novo ingresso")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> criar(@RequestBody IngressoRequest ingresso) {
        try {
            IngressoResponse ingressoBanco = ingressoService.salvaringresso(ingresso, 0L);
            return ResponseEntity.ok(ingressoBanco);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Método de atualização de status do ingresso", description = "Método responsável por atualizar apenas o status de um ingresso já emitido")
    public ResponseEntity<Void> atualizarStatus(@PathVariable Long id, @RequestBody AtualizarStatusIngressoRequest statusIngressoRequest) {
        var atualizarIngresso = ingressoService.atualizarStatus(statusIngressoRequest.statusIngressoRequest(),id);

        if(atualizarIngresso){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
        }



    @PutMapping("/{id}")
    @Operation(summary = "Método de atualização de ingresso", description = "Método responsável por atualizar os dados completos de um ingresso já emitido")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody IngressoRequest ingresso){


    try{
            IngressoResponse ingressoBanco = ingressoService.salvaringresso(ingresso, id);
            return ResponseEntity.ok(ingressoBanco);
        }catch (Exception e){
        return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //arrumar o tipo de ingresso
    @DeleteMapping("/{id}/excluir")
    @Operation(summary = "Método de exclusão de ingresso", description = "Método responsável por cancelar (soft delete) um ingresso emitido")
    public ResponseEntity<Void> excluir(@PathVariable Long id){

        var atualizarIngresso = ingressoService.atualizarStatus(EnumStatusIngresso.PAGO);

        if(atualizarIngresso){
            return ResponseEntity.ok().build();
        }
        return  ResponseEntity.notFound().build();
    }

    }





