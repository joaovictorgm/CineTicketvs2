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
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Long id){
        Usuario usuarioBanco = usuarioRepository.findById(id).orElse(null);
        if(usuarioBanco != null){

            return ResponseEntity.ok(usuarioBanco);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Método de criação de usuários", description = "Método responsável por cadastrar um novo usuário no sistema")
    public ResponseEntity<Usuario>criar(@RequestBody Usuario usuario){
        var usuarioBanco = usuarioRepository.save(usuario);
        return ResponseEntity.ok(usuarioBanco);
    }

    @PatchMapping("/{id}/status")
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
