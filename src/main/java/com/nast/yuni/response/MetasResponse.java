package com.nast.yuni.response;

import com.nast.yuni.domain.Metas;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetasResponse {
    private List<Metas> metas;
}
