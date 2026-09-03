package com.example.cineticket_pro.repository;

import com.example.cineticket_pro.entities.EnumStatusGerente;
import com.example.cineticket_pro.entities.EnumStatusUsuario;
import com.example.cineticket_pro.entities.Gerente;
import com.example.cineticket_pro.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GerenteRepository extends JpaRepository<Gerente,Long> {

    Optional<List<Gerente>> findByStatusNot(EnumStatusGerente statusGerente);
}
