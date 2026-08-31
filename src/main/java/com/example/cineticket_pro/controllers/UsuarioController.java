package com.example.cineticket_pro.controllers;


import com.example.cineticket_pro.DTOs.AtualizarStatusRequest;
import com.example.cineticket_pro.entities.EnumStatusUsuario;
import com.example.cineticket_pro.entities.Usuario;
import com.example.cineticket_pro.repository.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuários", description = "Métodos responsáveis pelo cadastro e consulta de usuários do sistema")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    @Operation(summary="Método de consulta de lista de usuários",description="Método responsável pela consulta de todos os usuários cadastrados")
    public ResponseEntity<?>listarTodos(){
        return ResponseEntity.ok(usuarioRepository.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Método de busca de usuário por ID", description = "Método responsável por buscar um usuário específico através do seu identificador")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Long id){//@PathVariable é uma anotação do Spring que extrai valores diretamente da URL da requisição e injeta como parâmetro no método do controller.
        Usuario usuarioBanco = usuarioRepository.findById(id).orElse(null);//.orElse(null) é um método do Optional que resolve o valor de dentro dele — retornando o valor real se ele existir, ou um valor padrão (nesse caso null) se estiver vazio.
        if(usuarioBanco != null){

            return ResponseEntity.ok(usuarioBanco);
        }
        return ResponseEntity.notFound().build();//.build() é o método final de um builder pattern (padrão de projeto) — ele finaliza a construção do objeto ResponseEntity e retorna a instância pronta pra uso.
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Método de criação de usuários", description = "Método responsável por cadastrar um novo usuário no sistema")
    public ResponseEntity<Usuario>criar(@RequestBody Usuario usuario){
        var usuarioBanco = usuarioRepository.save(usuario);
        return ResponseEntity.ok(usuarioBanco);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Método de atualização de status do usuário", description = "Método responsável por atualizar apenas o status de um usuário já cadastrado")
    public ResponseEntity<Void> atualizarStatus(@PathVariable Long id, @RequestBody AtualizarStatusRequest statusRequest){
        Usuario usuarioBanco = usuarioRepository.findById(id).orElse(null);
        if(usuarioBanco != null){
            usuarioBanco.setStatus(statusRequest.statusUsuario());
            usuarioRepository.save(usuarioBanco);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Método de atualização de usuário", description = "Método responsável por atualizar os dados completos de um usuário já cadastrado")
    public ResponseEntity<Usuario> atualizar(@PathVariable Long id, @RequestBody Usuario usuario){
        try{
            Usuario usuarioBanco = usuarioRepository.findById(id).orElse(null);
            if(usuarioBanco != null){
                usuarioBanco.setStatus(usuario.getStatus());
                usuarioBanco.setNome(usuario.getNome());
                usuarioBanco.setEmail(usuario.getEmail());
                usuarioBanco.setSenha(usuario.getSenha());
                usuarioRepository.save(usuarioBanco);
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.notFound().build();

        }catch (RuntimeException e){
            throw new RuntimeException(e);
        }

    }

    @DeleteMapping("/{id}/excluir")
    @Operation(summary = "Método de exclusão de usuário", description = "Método responsável por desativar (soft delete) um usuário cadastrado")
    public ResponseEntity<Void> excluir(@PathVariable Long id){

        Usuario usuarioBanco = usuarioRepository.findById(id).orElse(null);
        if(usuarioBanco != null){
            usuarioBanco.setStatus(EnumStatusUsuario.EXCLUIDO);
            usuarioRepository.save(usuarioBanco);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
