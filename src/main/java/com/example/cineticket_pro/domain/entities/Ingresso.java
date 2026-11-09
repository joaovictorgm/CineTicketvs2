package com.example.cineticket_pro.domain.entities;

import com.example.cineticket_pro.application.DTOs.EnumStatusIngresso;
import com.example.cineticket_pro.application.DTOs.EnumTipoIngresso;
import com.example.cineticket_pro.application.DTOs.IngressoRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Ingresso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String  assento;
    public String valorPago;
    public EnumTipoIngresso statusTipo = EnumTipoIngresso.INTEIRA ;
    public EnumStatusIngresso statusIngresso = EnumStatusIngresso.DISPONIVEL;
    public String  dataCompra;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "empresa_id",referencedColumnName = "id")
    private Empresa empresa;

    public Ingresso(IngressoRequest ingresso) {
        this.setAssento(ingresso.assento());
        this.setValorPago(ingresso.valorPago());
        this.setDataCompra(ingresso.dataCompra());
    }
}
