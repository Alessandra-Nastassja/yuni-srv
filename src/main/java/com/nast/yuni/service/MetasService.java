package com.nast.yuni.service;

import com.nast.yuni.domain.Metas;
import com.nast.yuni.repository.MetasRepository;
import com.nast.yuni.request.MetasRequest;
import com.nast.yuni.response.MetasResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MetasService {

    private final MetasRepository metasRepository;

    public MetasResponse listarMetas(){
        List<Metas> metas = metasRepository.findAllByOrderByPrazoAsc();

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
        
        Metas metasSalva = metasRepository.save(metas);

        return MetasResponse.builder()
                .metas(List.of(metasSalva))
                .build();
    }
}


