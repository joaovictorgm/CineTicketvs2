package com.example.cineticket_pro.domain.repository;

import com.example.cineticket_pro.domain.entities.Sessao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SessaoRepository extends JpaRepository<Sessao, Long> {

    Optional<Sessao> findByIdAndFilmeEmpresaId(Long id, Long empresaId);
    List<Sessao> findAllByFilmeEmpresaId(Long empresaId);




}
