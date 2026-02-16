package com.nast.yuni.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PatrimonioAtivos {

    private Integer ano;
    private Double contaCorrente;
    private ItemFinanceiro salario;
    private ItemFinanceiro meuNegocio;
    private Double investimentos;
    private ItemFinanceiro contaAReceber;
    private Double reservaDeEmergencia;
    private Double previdenciaPrivada;
    private ItemFinanceiro outros;
}
