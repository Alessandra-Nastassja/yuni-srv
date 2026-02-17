package com.nast.yuni.service;

import com.nast.yuni.domain.Metas;
import com.nast.yuni.request.MetasRequest;
import com.nast.yuni.response.MetasResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MetasService {
    public MetasResponse listarMetas(){
        List<Metas> metas = List.of(
                new Metas("Independência", 1000000.00, null, 2030),
                new Metas("Minha casa", 300000.00, null, 2028),
                new Metas("Reforma da casa", 15000.00, null, 2025),
                new Metas("Reserva de emergência", 35000.00, null, 2024));

        return MetasResponse.builder()
                .metas(metas)
                .build();
    }

    public MetasResponse criarMeta(MetasRequest request){
        Metas metas = new Metas(
                request.getNome(),
                request.getValorMeta(),
                request.getValorAtual(),
                request.getPrazo());
        
        return MetasResponse.builder()
                .metas(List.of(metas))
                .build();
    }
}
