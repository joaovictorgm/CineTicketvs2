package com.example.cineticket_pro.domain.entities;

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
    public EnumTipoExibicao status = EnumTipoExibicao.EXIBIÇÃO_2D;
    public EnumStatusSessao statusSessao= EnumStatusSessao.ATIVO;
    public String preco;
    public int assentosDisponiveis;
}
