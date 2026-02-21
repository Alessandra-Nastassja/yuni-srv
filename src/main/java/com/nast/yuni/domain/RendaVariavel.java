package com.nast.yuni.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "renda_variavel")
public class RendaVariavel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String tipoRendaVariavel;

    @Column(nullable = false)
    private Integer quantidade;

    @Column(nullable = false)
    private Double precoMedio;

    @Column(nullable = false)
    private Double valorAtual;

    private String corretora;

    @Column(nullable = false)
    private String categoriaRiscoRendaVariavel;

    // Campos específicos para Ações
    private java.time.LocalDate dataCompra;
    private Double dividendosRecebidos;
    private Integer irEstimadoAcoes;

    // Campos específicos para FII
    private Double dividendYield;
    private String irEstimadoFii;

    // Campos específicos para ETF
    private Integer irEstimadoEtf;
}

