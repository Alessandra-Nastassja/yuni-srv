package com.nast.yuni.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AtivosResponse {
    private List<AtivoResumoResponse> ativos;
}
