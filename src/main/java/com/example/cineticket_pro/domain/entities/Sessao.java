package com.example.cineticket_pro.domain.entities;

import com.example.cineticket_pro.application.DTOs.EnumStatusSessao;
import com.example.cineticket_pro.application.DTOs.EnumTipoExibicao;
import com.example.cineticket_pro.application.DTOs.SessaoRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sessao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String filme;//fk
    public String data;
    public String sala;
    @Enumerated(EnumType.STRING)
    public EnumTipoExibicao status = EnumTipoExibicao.EXIBICAO_2D;
    public EnumStatusSessao statusSessao= EnumStatusSessao.ATIVO;
    public String preco;
    public int assentosDisponiveis;

    public Sessao(SessaoRequest sessao) {
        this.setFilme(sessao.filme());
        this.setData(sessao.data());
        this.setSala(sessao.sala());
        this.setPreco(sessao.preco());
        this.setAssentosDisponiveis(sessao.assentosDisponiveis());

    }
}
