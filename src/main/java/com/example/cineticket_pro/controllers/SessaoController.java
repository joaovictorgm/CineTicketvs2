package com.example.cineticket_pro.controllers;

import com.example.cineticket_pro.DTOs.AtualizarSessaoRequest;
import com.example.cineticket_pro.entities.Sessao;
import com.example.cineticket_pro.repository.SessaoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sessoes")
@Tag(name="Sessões",description = "Métodos responsáveis pelo cadastro e consulta das sessões de exibição dos filmes")
public class SessaoController {

    @Autowired
    private SessaoRepository sessaoRepository;

    @GetMapping
    @Operation(summary = "Método de consulta de lista de sessões",description = "Método responsável pela consulta de todas as sessões cadastradas")
    public ResponseEntity<?>listarTodos(){
        return ResponseEntity.ok(sessaoRepository.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Método de busca de sessão por ID", description = "Método responsável por buscar uma sessão específica através do seu identificador")
    public ResponseEntity<Sessao>buscarPorId(@PathVariable Long id){
        Sessao sessaoBanco = sessaoRepository.findById(id).orElse(null);
        if(sessaoBanco != null){
            return ResponseEntity.ok(sessaoBanco);
        }
        return ResponseEntity.notFound().build();
    }
    @PostMapping
    @Operation(summary = "Método de criação de sessões",description = "Método responsável por cadastrar uma nova sessão de exibição")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Sessao>criar(@RequestBody Sessao sessao){
        var sessaoBanco = sessaoRepository.save(sessao);
        return ResponseEntity.ok(sessaoBanco);

    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Método de atualização de status da sessão", description = "Método responsável por atualizar apenas o status de exibição de uma sessão já cadastrada")
    public ResponseEntity<Void> atualizarStatus(@PathVariable Long id, @RequestBody AtualizarSessaoRequest sessaoRequest){
        Sessao sessaoBanco = sessaoRepository.findById(id).orElse(null);
        if(sessaoBanco != null){
            sessaoBanco.setStatus(sessaoRequest.statusExibicao());
            sessaoRepository.save(sessaoBanco);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("{id}")
    @Operation(summary = "Método de atualização de sessão", description = "Método responsável por atualizar os dados completos de uma sessão já cadastrada")
    public ResponseEntity<Sessao>atualizar(@PathVariable Long id, @RequestBody Sessao sessao){
        try {
            Sessao sessaoBanco = sessaoRepository.findById(id).orElse(null);
            if(sessaoBanco!=null){
                sessaoBanco.setStatus(sessao.getStatus());
                sessaoBanco.setData(sessao.getData());
                sessaoBanco.setFilme(sessao.getFilme());
                sessaoBanco.setSala(sessao.getSala());
                sessaoBanco.setPreco(sessao.getPreco());
                sessaoBanco.setAssentosDisponiveis(sessao.getAssentosDisponiveis());
                sessaoRepository.save(sessaoBanco);
                return ResponseEntity.ok().build();
            }

            return ResponseEntity.notFound().build();
        }catch (RuntimeException e){
            throw new RuntimeException(e);
        }
    }
    @DeleteMapping("/{id}/excluir")
    @Operation(summary = "Método de exclusão de sessão", description = "Método responsável por desativar (soft delete) uma sessão cadastrada")
    public ResponseEntity<Void> excluir(@PathVariable Long id){
        Sessao sessaoBanco = sessaoRepository.findById(id).orElse(null);
        if(sessaoBanco!=null){
            // sessaoBanco.setSituacao(EnumStatusSessao.DESATIVADO);
            sessaoRepository.save(sessaoBanco);
            return ResponseEntity.ok().build();

        }

        return ResponseEntity.notFound().build();
    }

}
