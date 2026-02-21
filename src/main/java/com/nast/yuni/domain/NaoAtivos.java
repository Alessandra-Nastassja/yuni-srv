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
@Table(name = "nao_ativos")
public class NaoAtivos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nome;

    @Column(nullable = false)
    private String tipo; // veiculos, imoveis, emprestimos, financiamentos, fgts, outros

    @Column(name = "valor_atual")
    private Double valorAtual;

    @Column(name = "data_compra", nullable = false, updatable = false)
    @lombok.Builder.Default
    private java.time.LocalDateTime dataCompra = java.time.LocalDateTime.now();
}

