package com.nast.yuni.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Metas {
    private String nome;
    private Double valorMeta;
    private Double valorAtual;
    private Integer prazo;
    
    public Metas(String nome, Double valorMeta, Double valorAtual, Integer prazo) {
        this.nome = nome;
        this.valorMeta = valorMeta;
        this.valorAtual = valorAtual;
        this.prazo = prazo;
    }
}
