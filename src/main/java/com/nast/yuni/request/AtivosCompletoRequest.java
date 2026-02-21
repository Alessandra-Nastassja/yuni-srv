package com.nast.yuni.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AtivosCompletoRequest {

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 30, message = "Nome não pode ter mais de 30 caracteres")
    private String nome;

    @NotBlank(message = "Tipo é obrigatório")
    private String tipo; // conta_corrente, meu_negocio, investimentos, contas_a_receber, reserva_emergencia, previdencia_privada, outros

    // Para conta_corrente e meu_negocio
    private String tipoFonteRenda;

    // Para tipos não-investimento (valor atual obrigatório se tipo != investimentos e != vazio)
    private Double valorAtual;

    // Para investimentos
    private String tipoInvestimento; // tesouro_direto, renda_fixa, renda_variavel, outros

    // Investimento em Tesouro Direto
    @Valid
    private TesoroDiretoRequest tesouroDireto;

    // Investimento em Renda Fixa
    @Valid
    private RendaFixaRequest rendaFixa;

    // Investimento em Renda Variável
    @Valid
    private RendaVariavelRequest rendaVariavel;

    // Para investimentos tipo "outros"
    private String categorizacaoOutros;
    private String risco; // baixo, medio, alto
}


