package com.example.cineticket_pro.domain.repository;

import com.example.cineticket_pro.domain.entities.Filme;
import org.hibernate.internal.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilmeRepository extends JpaRepository<Filme,Long> {
    List<Filme> findAllByEmpresa_id(Long empresa);
    Optional<Filme> findByIdAndEmpresaId(Long id, Long empresaId);
    List<Filme> findAllByEmpresaId(Long empresaId);
}
