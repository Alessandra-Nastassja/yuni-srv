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

    public MetasResponse obterMetaPorId(Long id){
        Metas meta = metasRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Meta não encontrada com ID: " + id));

        return MetasResponse.builder()
                .metas(List.of(meta))
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

    public MetasResponse atualizarMeta(Long id, MetasRequest request){
        Metas metaExistente = metasRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Meta não encontrada com ID: " + id));

        metaExistente = Metas.builder()
                .id(id)
                .nome(request.getNome())
                .valorMeta(request.getValorMeta())
                .valorAtual(request.getValorAtual())
                .prazo(request.getPrazo())
                .build();

        Metas metaAtualizada = metasRepository.save(metaExistente);

        return MetasResponse.builder()
                .metas(List.of(metaAtualizada))
                .build();
    }

    public void deletarMeta(Long id){
        Metas meta = metasRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Meta não encontrada com ID: " + id));

        metasRepository.deleteById(id);
    }
}


