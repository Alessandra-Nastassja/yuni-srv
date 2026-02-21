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
@Table(name = "renda_fixa")
public class RendaFixa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String tipoAtivoRendaFixa;

    @Column(nullable = false)
    private Double valorInvestido;

    @Column(nullable = false)
    private Double valorAtual;

    private String corretora;

    @Column(nullable = false)
    private java.time.LocalDate dataCompra;

    @Column(nullable = false)
    private java.time.LocalDate dataVencimento;

    @Column(nullable = false)
    private String tipoTaxa;

    private Double taxaContratada;

    private Double percentualCdi;

    private Double cdiAtual;

    private Double ipcaTaxa;

    private String tipoDebenture;

    @Column(nullable = false)
    private String categoriaRiscoRendaFixa;

    @Column(nullable = false)
    private Boolean isento;

    private Double irEstimado;

    private Double valorFinalEstimado;
}

