package com.nast.yuni.service;

import com.nast.yuni.domain.ItemFinanceiro;
import com.nast.yuni.domain.PatrimonioAtivos;
import com.nast.yuni.domain.PatrimonioNaoAtivos;
import com.nast.yuni.domain.PatrimonioEvolucao;
import com.nast.yuni.response.PatrimonioResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatrimonioService {
    public PatrimonioResponse patrimonio(){
        PatrimonioAtivos ativos2026 = new PatrimonioAtivos(
                2026,
                0.0,
                new ItemFinanceiro(13878.24, "Fonte de renda 1"),
                new ItemFinanceiro(0.0, "Fonte de renda 2"),
                200000.0,
                new ItemFinanceiro(415.38, "Dividendos e JCP"),
                32123.85,
                0.0,
                new ItemFinanceiro(0.0, "13 salário, férias, bônus, etc.")
        );

        PatrimonioNaoAtivos naoAtivos2026 = PatrimonioNaoAtivos.builder()
                .ano(2026)
                .veiculos(7335.0)
                .imoveis(0.0)
                .fgts(55448.7)
                .objetosDeValor(new ItemFinanceiro(10000.0, "Joias, obras de arte, etc."))
                .outros(new ItemFinanceiro(0.0, "Qualquer outro ativo"))
                .build();

        List<PatrimonioEvolucao> evolucao = List.of(
                new PatrimonioEvolucao(2026, 232123.85),
                new PatrimonioEvolucao(2025, 222448.47),
                new PatrimonioEvolucao(2024, 199276.15),
                new PatrimonioEvolucao(2023, 199276.15),
                new PatrimonioEvolucao(2022, 126141.67),
                new PatrimonioEvolucao(2021, 99470.32),
                new PatrimonioEvolucao(2020, 64501.39)
        );

        return PatrimonioResponse.builder()
                .ativos(List.of(ativos2026))
                .naoAtivos(List.of(naoAtivos2026))
                .evolucao(evolucao)
                .build();

    }
}
