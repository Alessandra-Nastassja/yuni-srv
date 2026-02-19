package com.nast.yuni.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AtivosRequest {
    private String nome;
    private String tipo;
//    private String categoriaRisco;
    private Double valorAtual;
}
