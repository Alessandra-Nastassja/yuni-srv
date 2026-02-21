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
public class RendaFixaRequest {

    @NotBlank(message = "Tipo de ativo de renda fixa é obrigatório")
    private String tipoAtivoRendaFixa; // cdb, lc, debenture, lci, lca, cri, cra, outros

    @NotNull(message = "Valor investido é obrigatório")
    private Double valorInvestido;

    @NotNull(message = "Valor atual é obrigatório")
    private Double valorAtual;

    private String corretora;

    @NotNull(message = "Data de compra é obrigatória")
    private LocalDate dataCompra;

    @NotNull(message = "Data de vencimento é obrigatória")
    private LocalDate dataVencimento;

    @NotBlank(message = "Tipo de taxa é obrigatório")
    private String tipoTaxa; // prefixado, pos_fixado_cdi, ipca

    // Para tipoTaxa = prefixado
    private Double taxaContratada;

    // Para tipoTaxa = pos_fixado_cdi
    private Double percentualCdi;
    private Double cdiAtual; // somente leitura - preenchido automaticamente

    // Para tipoTaxa = ipca
    private Double ipcaTaxa;

    // Para tipoAtivoRendaFixa = debenture
    private String tipoDebenture; // comum, incentivada

    @NotBlank(message = "Categoria de risco é obrigatória")
    private String categoriaRiscoRendaFixa; // baixo, medio, alto

    // IR
    private Boolean isento; // somente leitura - calculado automaticamente
    private Double irEstimado; // editável apenas se tipoAtivoRendaFixa = outros

    private Double valorFinalEstimado; // somente leitura - preenchido automaticamente
}

