package com.nast.yuni.response;

import com.nast.yuni.domain.PatrimonioAtivos;
import com.nast.yuni.domain.PatrimonioEvolucao;
import com.nast.yuni.domain.PatrimonioNaoAtivos;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatrimonioResponse {
    private List<PatrimonioAtivos> ativos;
    private List<PatrimonioNaoAtivos> naoAtivos;
    private List<PatrimonioEvolucao> evolucao;
}
