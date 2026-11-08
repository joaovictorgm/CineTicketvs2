package com.example.cineticket_pro.domain.repository;

import com.example.cineticket_pro.domain.entities.Empresa;
import com.example.cineticket_pro.domain.entities.Gerente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

    boolean existsByCnpj(String cnpj);


}
