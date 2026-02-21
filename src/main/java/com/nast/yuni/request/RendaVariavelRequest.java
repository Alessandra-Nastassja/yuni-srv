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

    // Campo específico para Ações
    private LocalDate dataCompra;
}

