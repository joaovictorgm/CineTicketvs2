package com.example.cineticket_pro.domain.entities;

import com.example.cineticket_pro.application.DTOs.EnumStatusIngresso;
import com.example.cineticket_pro.application.DTOs.EnumTipoIngresso;
import com.example.cineticket_pro.application.DTOs.IngressoRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
    @Enumerated(EnumType.STRING)
    public EnumTipoIngresso statusTipo = EnumTipoIngresso.INTEIRA ;
    @Enumerated(EnumType.STRING)
    public EnumStatusIngresso statusIngresso = EnumStatusIngresso.DISPONIVEL;
    public String  dataCompra;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sessao_id", referencedColumnName = "id", nullable = false)
    private Sessao sessao;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "filme_id", referencedColumnName = "id", nullable = false)
    private Filme filme;



    public Ingresso(IngressoRequest ingresso) {
        this.setAssento(ingresso.assento());
        this.setValorPago(ingresso.valorPago());
        this.setDataCompra(ingresso.dataCompra());
    }
}
