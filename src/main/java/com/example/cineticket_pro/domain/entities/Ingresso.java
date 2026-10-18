package com.example.cineticket_pro.domain.entities;

import com.example.cineticket_pro.application.DTOs.EnumStatusIngresso;
import com.example.cineticket_pro.application.DTOs.EnumTipoIngresso;
import com.example.cineticket_pro.application.DTOs.IngressoRequest;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Ingresso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String sessao;//fk
    public String filme;//fk
    public String  assento;
    public String valorPago;
    public EnumTipoIngresso statusTipo = EnumTipoIngresso.INTEIRA ;
    public EnumStatusIngresso statusIngresso = EnumStatusIngresso.DISPONIVEL;
    public String  dataCompra;

    public Ingresso(IngressoRequest ingresso) {
        this.setSessao(ingresso.sessao());
        this.setFilme(ingresso.filme());
        this.setAssento(ingresso.assento());
        this.setValorPago(ingresso.valorPago());
        this.setDataCompra(ingresso.dataCompra());
    }
}
