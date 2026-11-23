package com.example.cineticket_pro.domain.entities;

import com.example.cineticket_pro.application.DTOs.SessaoRequest;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Sessao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    public Long id;
    public String data;
    public String sala;
    @Enumerated(EnumType.STRING)
    public EnumTipoExibicao status = EnumTipoExibicao.EXIBICAO_2D;
    @Enumerated(EnumType.STRING)
    public EnumStatusSessao statusSessao= EnumStatusSessao.ATIVO;
    public String preco;
    public int assentosDisponiveis;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "filme_id", nullable = false)
    private Filme filme;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "empresa_id", referencedColumnName = "id")
    public Empresa empresa;








    public Sessao(SessaoRequest sessao) {
        this.setData(sessao.data());
        this.setSala(sessao.sala());
        this.setPreco(sessao.preco());
        this.setAssentosDisponiveis(sessao.assentosDisponiveis());

    }



}
