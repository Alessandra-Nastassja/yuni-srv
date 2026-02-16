package com.nast.yuni.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@AllArgsConstructor
public class PatrimonioNaoAtivos {
    private Integer ano;
    private Double veiculos;
    private Double imoveis;
    private Double fgts;
    private ItemFinanceiro objetosDeValor;
    private ItemFinanceiro outros;
}
