package com.nast.yuni.response;

import com.nast.yuni.domain.NaoAtivos;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NaoAtivosResponse {
    private List<NaoAtivos> naoAtivos;
}

