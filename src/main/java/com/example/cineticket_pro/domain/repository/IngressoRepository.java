package com.example.cineticket_pro.domain.repository;

import com.example.cineticket_pro.domain.entities.Ingresso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngressoRepository extends JpaRepository<Ingresso, Long> {
}
