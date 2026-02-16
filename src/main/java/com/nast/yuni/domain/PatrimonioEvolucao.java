package com.nast.yuni.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@AllArgsConstructor
public class PatrimonioEvolucao {
    private Integer ano;
    private Double valor;
}
