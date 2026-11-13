package com.example.cineticket_pro.presentation;


import com.example.cineticket_pro.application.DTOs.*;
import com.example.cineticket_pro.application.service.GerenteService;
import com.example.cineticket_pro.application.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuários", description = "Métodos responsáveis pelo cadastro e consulta de usuários do sistema")
public class UsuarioController {



    @Autowired
    private UsuarioService usuarioService;


    @GetMapping
    @Operation(summary="Método de consulta de lista de usuários",description="Método responsável pela consulta de todos os usuários cadastrados")
    public ResponseEntity<List<UsuarioResponse>>listarTodos(){
        return ResponseEntity.ok(usuarioService.listarTodosUsuariosTable());
    }


    @GetMapping("/{id}")
    @Operation(summary = "Método de busca de usuário por ID", description = "Método responsável por buscar um usuário específico através do seu identificador")
    public ResponseEntity<UsuarioResponse> buscarPorId(@PathVariable Long id){//@PathVariable é uma anotação do Spring que extrai valores diretamente da URL da requisição e injeta como parâmetro no método do controller.
        UsuarioResponse usuarioBanco = usuarioService.buscarPorId(id);//.orElse(null) é um método do Optional que resolve o valor de dentro dele — retornando o valor real se ele existir, ou um valor padrão (nesse caso null) se estiver vazio.
        if(usuarioBanco != null){

            return ResponseEntity.ok(usuarioBanco);
        }
        return ResponseEntity.notFound().build();//.build() é o método final de um builder pattern (padrão de projeto) — ele finaliza a construção do objeto ResponseEntity e retorna a instância pronta pra uso.
    }

    @PostMapping
    @Operation(summary = "Método de criação de usuários", description = "Método responsável por cadastrar um novo usuário no sistema")
    public ResponseEntity<?>criar(@RequestBody UsuarioRequest usuario) {
        try {
            UsuarioResponse usuarioBanco = usuarioService.salvarUsuario(usuario, 0L);
            return ResponseEntity.ok(usuarioBanco);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Método de atualização de status do usuário", description = "Método responsável por atualizar apenas o status de um usuário já cadastrado")
    public ResponseEntity<Void> atualizarStatus(@PathVariable Long id, @RequestBody AtualizarStatusRequest statusRequest){
        var atualizarUsuario = usuarioService.atualizarStatus(statusRequest.statusUsuario(),id);

        if(atualizarUsuario){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Método de atualização de usuário", description = "Método responsável por atualizar os dados completos de um usuário já cadastrado")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody UsuarioRequest usuario){
        try{
            UsuarioResponse usuarioBanco = usuarioService.salvarUsuario(usuario,id);
           return ResponseEntity.ok(usuarioBanco);
            }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}/excluir")
    @Operation(summary = "Método de exclusão de usuário", description = "Método responsável por desativar (soft delete) um usuário cadastrado")
    public ResponseEntity<Void> excluir(@PathVariable Long id){

        var atualizarUsuario = usuarioService.atualizarStatus(EnumStatusUsuario.EXCLUIDO,id);

        if(atualizarUsuario){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}/entrarsessao")
    @Operation(summary = "Registrar entrada do usuário em uma sessão", description = "Associa o usuário informado a uma sessão de cinema. Retorna 200 em caso de sucesso e 404 se o usuário ou a sessão não forem encontrados.")
    public ResponseEntity<?> entrarsessao(@PathVariable Long id , @RequestBody EntrarSessaoRequest entrarSessaoRequest){

        boolean retornoEntrarSessao = usuarioService.entrarsessao(id, entrarSessaoRequest);

        if(retornoEntrarSessao){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/buscarusuariologado")
    @Operation(summary = "Usuario logado", description = "Usuario logado")
    public ResponseEntity<UsuarioResponse> buscarUsuarioLogado(){
        return ResponseEntity.ok(usuarioService.buscarUsuarioLogado());
    }
}

