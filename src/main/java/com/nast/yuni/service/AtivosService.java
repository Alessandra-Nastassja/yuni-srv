package com.nast.yuni.service;

import com.nast.yuni.domain.Ativos;
import com.nast.yuni.repository.AtivosRepository;
import com.nast.yuni.request.AtivosRequest;
import com.nast.yuni.response.AtivosResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AtivosService {
    private final AtivosRepository repository;

    public AtivosResponse listarAtivos(){
        List<Ativos> ativos = repository.findAllByOrderByValorAtualDesc();

        return AtivosResponse.builder()
                .ativos(ativos)
                .build();
    }

    public AtivosResponse criarAtivo(AtivosRequest request){
        Ativos ativo = Ativos.builder()
                .nome(request.getNome())
                .tipo(request.getTipo())
//                .categoriaRisco(request.getCategoriaRisco())
                .valorAtual(request.getValorAtual())
                .build();

        Ativos ativoSalvo = repository.save(ativo);

        return AtivosResponse.builder()
                .ativos(List.of(ativoSalvo))
                .build();
    }
}
