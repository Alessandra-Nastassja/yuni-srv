package com.nast.yuni.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class Metas {
    private String nome;
    private Double valorMeta;
    private Integer prazo;
}
