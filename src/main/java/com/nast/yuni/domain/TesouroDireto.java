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
@Table(name = "tesouro_direto")
public class TesouroDireto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String tipoTesouro;

    @Column(nullable = false)
    private Double valorInvestido;

    @Column(nullable = false)
    private Double valorAtual;

    @Column(nullable = false)
    private java.time.LocalDate dataCompra;

    @Column(nullable = false)
    private java.time.LocalDate dataVencimento;

    private String corretora;

    @Column(nullable = false)
    private Double taxaRentabilidade;

    // Risco sempre baixo
    @Column(nullable = false)
    @lombok.Builder.Default
    private String risco = "baixo";
}

