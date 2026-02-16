package com.nast.yuni.service;

import com.nast.yuni.domain.Metas;
import com.nast.yuni.response.MetasResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MetasService {
    public MetasResponse metas(){
        List<Metas> metas = List.of(
                new Metas("Independência", 1000000.00, 2030),
                new Metas("Minha casa", 300000.00, 2028),
                new Metas("Reforma da casa", 15000.00, 2025),
                new Metas("Reserva de emergência", 35000.00, 2024));



        return MetasResponse.builder()
                .metas(metas)
                .build();
    }
}
