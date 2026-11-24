package com.example.cineticket_pro.application.port;

import com.example.cineticket_pro.application.DTOs.FilmeRequest;
import com.example.cineticket_pro.application.DTOs.FilmeResponse;
import com.example.cineticket_pro.domain.entities.EnumStatusFilme;

import java.util.List;

public interface FilmeUseCases {
    List<FilmeResponse> listarTodosFilmesTable();
    FilmeResponse salvarFilme(FilmeRequest filme, Long id);
    FilmeResponse buscarPorId(Long id);
    boolean atualizarStatus(EnumStatusFilme status, Long id);
}