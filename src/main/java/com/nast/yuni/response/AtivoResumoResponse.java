package com.nast.yuni.response;

import com.nast.yuni.domain.Ativos;
import com.nast.yuni.domain.AtivosCompleto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AtivoResumoResponse {
    private Long id;
    private String nome;
    private String tipo;
    private Double valorAtual;
    private String tipoInvestimento;
    private String risco;
    private LocalDateTime dataCriacao;

    public static AtivoResumoResponse fromAtivo(Ativos ativo) {
        return AtivoResumoResponse.builder()
                .id(ativo.getId())
                .nome(ativo.getNome())
                .tipo(ativo.getTipo())
                .valorAtual(ativo.getValorAtual())
                .dataCriacao(ativo.getDataCriacao())
                .build();
    }

    public static AtivoResumoResponse fromAtivoCompleto(AtivosCompleto ativo) {
        return AtivoResumoResponse.builder()
                .id(ativo.getId())
                .nome(ativo.getNome())
                .tipo(ativo.getTipo())
                .valorAtual(ativo.getValorAtual())
                .tipoInvestimento(ativo.getTipoInvestimento())
                .risco(ativo.getRisco())
                .dataCriacao(ativo.getDataCriacao())
                .build();
    }
}

