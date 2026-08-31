package com.example.cineticket_pro.controllers;

import com.example.cineticket_pro.DTOs.AtualizarStatusIngressoRequest;
import com.example.cineticket_pro.entities.EnumTipoIngresso;
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
    public ResponseEntity<?> listarTodos() {
        return ResponseEntity.ok(ingressoRepository.findAll());
    }


    @GetMapping("/{id}")
    @Operation(summary = "Método de busca de ingresso por ID", description = "Método responsável por buscar um ingresso específico através do seu identificador")
    public ResponseEntity<Ingresso> buscarPorId(@PathVariable Long id) {
        Ingresso ingressoBanco = ingressoRepository.findById(id).orElse(null);
        if (ingressoBanco != null) {
            return ResponseEntity.ok(ingressoBanco);
        }
        return ResponseEntity.notFound().build();

    }

    @PostMapping
    @Operation(summary = "Método de criação de ingressos", description = "Método responsável por registrar a emissão de um novo ingresso")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Ingresso> criar(@RequestBody Ingresso ingresso) {
        var ingressoBanco = ingressoRepository.save(ingresso);
        return ResponseEntity.ok(ingressoBanco);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Método de atualização de status do ingresso", description = "Método responsável por atualizar apenas o status de um ingresso já emitido")
    public ResponseEntity<Void> atualizarStatus(@PathVariable Long id, @RequestBody AtualizarStatusIngressoRequest statusIngressoRequest) {
        Ingresso ingressoBanco = ingressoRepository.findById(id).orElse(null);
        if (ingressoBanco != null) {
            ingressoBanco.setStatus(statusIngressoRequest.statusIngressoRequest());
            ingressoRepository.save(ingressoBanco);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();

    }
    @PutMapping("/{id}")
    @Operation(summary = "Método de atualização de ingresso", description = "Método responsável por atualizar os dados completos de um ingresso já emitido")
    public ResponseEntity<Ingresso> atualizar(@PathVariable Long id, @RequestBody Ingresso ingresso){
        try{
            Ingresso ingressoBanco = ingressoRepository.findById(id).orElse(null);
            if(ingressoBanco != null){
                ingressoBanco.setStatus(ingresso.getStatus());
                ingressoBanco.setSessao(ingresso.getSessao());
                ingressoBanco.setFilme(ingresso.getFilme());
                ingressoBanco.setAssento(ingresso.getAssento());
                ingressoBanco.setDataCompra(ingresso.getDataCompra());
                ingressoBanco.setValorPago(ingresso.getValorPago());
                ingressoRepository.save(ingressoBanco);
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.notFound().build();
        }catch (RuntimeException e){
            throw new RuntimeException(e);
        }
    }
    @DeleteMapping("/{id}/excluir")
    @Operation(summary = "Método de exclusão de ingresso", description = "Método responsável por cancelar (soft delete) um ingresso emitido")
    public ResponseEntity<Void> excluir(@PathVariable Long id){
        Ingresso ingressoBanco = ingressoRepository.findById(id).orElse(null);
        if(ingressoBanco != null){
            //ingressoBanco.setSituacao(EnumStatusIngresso.PAGO);
            ingressoRepository.save(ingressoBanco);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}




