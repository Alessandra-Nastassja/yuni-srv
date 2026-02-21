package com.nast.yuni.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "metas")
public class Metas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private Double valorMeta;

    @Column
    private Double valorAtual;

    @Column(nullable = false)
    private Integer prazo;
    
    public Metas(String nome, Double valorMeta, Double valorAtual, Integer prazo) {
        this.nome = nome;
        this.valorMeta = valorMeta;
        this.valorAtual = valorAtual;
        this.prazo = prazo;
    }

    public Double getPercentualAlcance() {
        if (valorMeta == null || valorMeta == 0) {
            return 0.0;
        }
        if (valorAtual == null) {
            return 0.0;
        }
        return (valorAtual / valorMeta) * 100;
    }
}
