package com.example.cineticket_pro.domain.repository;

import com.example.cineticket_pro.domain.entities.Filme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmeRepository extends JpaRepository<Filme,Long> {
}
