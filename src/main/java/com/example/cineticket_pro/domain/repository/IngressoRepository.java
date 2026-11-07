package com.example.cineticket_pro.domain.repository;

import com.example.cineticket_pro.domain.entities.Ingresso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IngressoRepository extends JpaRepository<Ingresso, Long> {

    List<Ingresso> getIngressoByEmpresa_id(Long empresa);

    Optional<Ingresso> findByIdAndEmpresaId(Long id, Long empresaId);

    List<Ingresso> findAllByEmpresaId(Long empresaId);

}
