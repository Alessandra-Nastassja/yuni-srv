package com.nast.yuni.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NaoAtivosRequest {
    private String nome;
    private String tipo; // veiculos, imoveis, emprestimos, financiamentos, outros
    private Double valorAtual;
}

