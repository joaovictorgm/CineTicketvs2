package com.example.cineticket_pro.domain.entities;

public enum EnumTipoExibicao {
    EXIBICAO_2D("2D"),
    EXIBICAO_3D("3D");

    private final String descricao;
    EnumTipoExibicao(String descricao) { this.descricao = descricao; }
    public String getDescricao() { return descricao; }



}
