package com.example.cineticket_pro.controllers;

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

    @PostMapping
    @Operation(summary = "Método de criação de gerentes",description = "Método responsável por cadastrar um novo gerente no sistema")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Gerente>criar(@RequestBody Gerente gerente){
        var gerenteBanco =  gerenteRepository.save(gerente);
        return ResponseEntity.ok(gerenteBanco);
    }
}
