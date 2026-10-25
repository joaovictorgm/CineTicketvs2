package com.example.cineticket_pro.domain.repository;

import com.example.cineticket_pro.domain.entities.Sessao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SessaoRepository extends JpaRepository<Sessao, Long> {
    List<Sessao> getSessoesByEmpresa_Id(Long empresa);
}
