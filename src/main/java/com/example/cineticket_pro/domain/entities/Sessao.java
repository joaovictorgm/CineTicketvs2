package com.example.cineticket_pro.domain.entities;

import com.example.cineticket_pro.application.DTOs.EnumStatusSessao;
import com.example.cineticket_pro.application.DTOs.EnumTipoExibicao;
import com.example.cineticket_pro.application.DTOs.SessaoRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    public String filme;//fk
    public String data;
    public String sala;
    @Enumerated(EnumType.STRING)
    public EnumTipoExibicao status = EnumTipoExibicao.EXIBICAO_2D;
    public EnumStatusSessao statusSessao= EnumStatusSessao.ATIVO;
    public String preco;
    public int assentosDisponiveis;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "empresa_id",referencedColumnName = "id")
    private Empresa empresa;

    @ManyToMany
    @JoinTable(
            name = "tb_cadastrofilme",
            joinColumns = @JoinColumn(name = "sessao_id"),
            inverseJoinColumns = @JoinColumn(name = "filme_id")
    )

    private Set<Filme> filmes = new HashSet<>();

    public void juntarfilmesessao(Filme filme){
        this.filmes.add(filme);
    }



    public Sessao(SessaoRequest sessao) {
        this.setFilme(sessao.filme());
        this.setData(sessao.data());
        this.setSala(sessao.sala());
        this.setPreco(sessao.preco());
        this.setAssentosDisponiveis(sessao.assentosDisponiveis());

    }



}
