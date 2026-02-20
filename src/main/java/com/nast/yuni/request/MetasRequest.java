package com.nast.yuni.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetasRequest {
    private String nome;
    private Double valorMeta;
    private Double valorAtual;
    private Integer prazo;
}