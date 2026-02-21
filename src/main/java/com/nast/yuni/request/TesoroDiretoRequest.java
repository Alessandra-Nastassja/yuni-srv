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
public class TesoroDiretoRequest {

    @NotBlank(message = "Tipo de tesouro é obrigatório")
    private String tipoTesouro; // tesouro_prefixado, tesouro_selic, tesouro_ipca

    @NotNull(message = "Valor investido é obrigatório")
    private Double valorInvestido;

    @NotNull(message = "Valor atual é obrigatório")
    private Double valorAtual;

    @NotNull(message = "Data de compra é obrigatória")
    private LocalDate dataCompra;

    @NotNull(message = "Data de vencimento é obrigatória")
    private LocalDate dataVencimento;

    private String corretora;

    @NotNull(message = "Taxa de rentabilidade é obrigatória")
    private Double taxaRentabilidade;

    // Risco é sempre "baixo" - preenchido automaticamente
}

