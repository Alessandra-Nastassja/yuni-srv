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
@Table(name = "ativos_completo")
public class AtivosCompleto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String nome;

    @Column(nullable = false)
    private String tipo;

    private String tipoFonteRenda;

    private Double valorAtual;

    @Column(nullable = false)
    private String tipoInvestimento;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "tesouro_direto_id")
    private TesouroDireto tesouroDireto;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "renda_fixa_id")
    private RendaFixa rendaFixa;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "renda_variavel_id")
    private RendaVariavel rendaVariavel;

    private String categorizacao;

    @Column(nullable = false)
    private String risco;

    @Column(nullable = false, updatable = false)
    @lombok.Builder.Default
    private java.time.LocalDateTime dataCriacao = java.time.LocalDateTime.now();

    @Column(nullable = false)
    @lombok.Builder.Default
    private java.time.LocalDateTime dataAtualizacao = java.time.LocalDateTime.now();
}


