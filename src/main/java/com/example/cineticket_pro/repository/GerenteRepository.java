package com.example.cineticket_pro.repository;

import com.example.cineticket_pro.entities.Gerente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GerenteRepository extends JpaRepository<Gerente,Long> {
}
