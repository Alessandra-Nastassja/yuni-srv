package com.nast.yuni.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RendaVariavelRequest {

    @NotBlank(message = "Tipo de renda variável é obrigatório")
    private String tipoRendaVariavel; // acoes, fii, etf, outros

    @NotNull(message = "Quantidade é obrigatória")
    private Integer quantidade;

    @NotNull(message = "Preço médio é obrigatório")
    private Double precoMedio;

    @NotNull(message = "Valor atual é obrigatório")
    private Double valorAtual;

    private String corretora;

    @NotBlank(message = "Categoria de risco é obrigatória")
    private String categoriaRiscoRendaVariavel; // baixo, medio, alto

    // Campos específicos para Ações
    private LocalDate dataCompra;
    private Double dividendosRecebidos;
    private Integer irEstimadoAcoes; // 15 ou 20

    // Campos específicos para FII
    private Double dividendYield;
    private String irEstimadoFii; // percentual em texto

    // Campos específicos para ETF
    private Integer irEstimadoEtf; // 15 ou 20
}

