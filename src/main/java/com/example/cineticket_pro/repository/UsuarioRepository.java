package com.example.cineticket_pro.repository;

import com.example.cineticket_pro.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario,Long> {
}
