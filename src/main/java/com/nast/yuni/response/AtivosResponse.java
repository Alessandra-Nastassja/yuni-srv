package com.nast.yuni.response;

import com.nast.yuni.domain.Ativos;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AtivosResponse {
    private List<Ativos> ativos;
}
