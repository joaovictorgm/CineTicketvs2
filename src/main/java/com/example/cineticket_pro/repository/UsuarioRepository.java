package com.example.cineticket_pro.repository;

import com.example.cineticket_pro.entities.EnumStatusUsuario;
import com.example.cineticket_pro.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Long> {

    boolean existsUsuarioByEmailAndSenha(String email, String senha);

    Optional<List<Usuario>> findByStatusNot(EnumStatusUsuario statusUsuario);

    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findByResetToken(String resetToken);

}
