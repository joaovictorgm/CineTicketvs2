package com.example.cineticket_pro.domain.repository;

import com.example.cineticket_pro.domain.entities.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpresaRepository extends JpaRepository <Empresa,Long>{

    boolean existsByCnpj(String cnpj);
}
