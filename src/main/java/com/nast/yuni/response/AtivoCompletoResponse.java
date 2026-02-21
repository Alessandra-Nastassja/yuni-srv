package com.nast.yuni.response;

import com.nast.yuni.domain.AtivosCompleto;
import com.nast.yuni.domain.TesouroDireto;
import com.nast.yuni.domain.RendaFixa;
import com.nast.yuni.domain.RendaVariavel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AtivoCompletoResponse {
    private Long id;
    private String nome;
    private String tipo;
    private String tipoFonteRenda;
    private Double valorAtual;
    private String tipoInvestimento;
    private String risco;
    private String categorizacaoOutros;
    private TesouroDireto tesouroDireto;
    private RendaFixa rendaFixa;
    private RendaVariavel rendaVariavel;
    private java.time.LocalDateTime dataCriacao;
    private java.time.LocalDateTime dataAtualizacao;

    public static AtivoCompletoResponse fromEntity(AtivosCompleto ativo) {
        return AtivoCompletoResponse.builder()
                .id(ativo.getId())
                .nome(ativo.getNome())
                .tipo(ativo.getTipo())
                .tipoFonteRenda(ativo.getTipoFonteRenda())
                .valorAtual(ativo.getValorAtual())
                .tipoInvestimento(ativo.getTipoInvestimento())
                .risco(ativo.getRisco())
                .categorizacaoOutros(ativo.getCategorizacao())
                .tesouroDireto(ativo.getTesouroDireto())
                .rendaFixa(ativo.getRendaFixa())
                .rendaVariavel(ativo.getRendaVariavel())
                .dataCriacao(ativo.getDataCriacao())
                .dataAtualizacao(ativo.getDataAtualizacao())
                .build();
    }
}

