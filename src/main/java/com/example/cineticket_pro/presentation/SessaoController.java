package com.example.cineticket_pro.presentation;

import com.example.cineticket_pro.application.DTOs.*;
import com.example.cineticket_pro.application.service.SessaoService;
import com.example.cineticket_pro.domain.entities.Sessao;
import com.example.cineticket_pro.domain.repository.SessaoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sessoes")
@Tag(name="Sessões",description = "Métodos responsáveis pelo cadastro e consulta das sessões de exibição dos filmes")
public class SessaoController {


    @Autowired
    private SessaoService sessaoService;

    @Autowired
    private SessaoRepository sessaoRepository;

    @GetMapping
    @Operation(summary = "Método de consulta de lista de sessões",description = "Método responsável pela consulta de todas as sessões cadastradas")
    public ResponseEntity<List<SessaoResponse>>listarTodos(){
        return ResponseEntity.ok(sessaoService.listarTodasSessoesTable());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Método de busca de sessão por ID", description = "Método responsável por buscar uma sessão específica através do seu identificador")
    public ResponseEntity<SessaoResponse>buscarPorId(@PathVariable Long id){
        SessaoResponse sessaoBanco = sessaoService.buscarPorId(id);
        if(sessaoBanco != null){
            return ResponseEntity.ok(sessaoBanco);
        }
        return ResponseEntity.notFound().build();
    }
    @PostMapping
    @Operation(summary = "Método de criação de sessões",description = "Método responsável por cadastrar uma nova sessão de exibição")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?>criar(@RequestBody SessaoRequest sessao) {
        try {
            SessaoResponse sessaoBanco = sessaoService.salvarSessao(sessao, 0l);
            return ResponseEntity.ok(sessaoBanco);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Método de atualização de tipo de  sessão", description = "Método responsável por atualizar apenas o status de exibição de uma sessão já cadastrada")
    public ResponseEntity<Void> atualizarStatus(@PathVariable Long id, @RequestBody AtualizarSessaoRequest sessaoRequest){
        var atualizarTipoSessao = sessaoService.atualizarTipoSessao(sessaoRequest.statusExibicao(),id);
       if(atualizarTipoSessao){
           return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
    @PatchMapping("/{id}/status-sessao")
    @Operation(summary = "Método de atualização do status ativo/desativado da sessão", description = "Método responsável por ativar ou desativar uma sessão já cadastrada")
    public ResponseEntity<Void> atualizarStatusSessao(@PathVariable Long id, @RequestBody AtualizarStatusSessaoRequest statusRequest){
        var atualizarStatusSessao = sessaoService.atualizarStatusSessao(statusRequest.statusSessao(),id);
        if(atualizarStatusSessao){
            return  ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}/tipo-exibicao")
    @Operation(summary = "Atualização do tipo de exibição do filme",
            description = "Atualiza apenas o tipo de exibição (2D, 3D) de um filme cadastrado")
    public ResponseEntity<Void> atualizarTipoExibicao(@PathVariable Long id,
                                                      @RequestBody AtualizarTipoExibicaoRequest request) {
        Sessao sessao = sessaoRepository.findById(id).orElse(null);
        if (sessao == null) {
            return ResponseEntity.notFound().build();
        }
        sessao.setStatus(request.tipoExibicao());
        sessaoRepository.save(sessao);
        return ResponseEntity.ok().build();
    }


    @PutMapping("{id}")
    @Operation(summary = "Método de atualização de sessão", description = "Método responsável por atualizar os dados completos de uma sessão já cadastrada")
    public ResponseEntity<?>atualizar(@PathVariable Long id, @RequestBody SessaoRequest sessao){
        try{
            SessaoResponse sessaoBanco = sessaoService.salvarSessao(sessao, id);
            return ResponseEntity.ok(sessaoBanco);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }

    // fazer a migração mais tarde
    @DeleteMapping("/{id}/excluir")
    @Operation(summary = "Método de exclusão de sessão", description = "Método responsável por desativar (soft delete) uma sessão cadastrada")
    public ResponseEntity<Void> excluir(@PathVariable Long id){
        Sessao sessaoBanco = sessaoRepository.findById(id).orElse(null);
        if(sessaoBanco!=null){
            sessaoBanco.setStatusSessao(EnumStatusSessao.DESATIVADO);
            sessaoRepository.save(sessaoBanco);
            return ResponseEntity.ok().build();

        }

        return ResponseEntity.notFound().build();
    }

}
