package com.nast.yuni.service;

import com.nast.yuni.domain.Ativos;
import com.nast.yuni.repository.AtivosRepository;
import com.nast.yuni.request.AtivosRequest;
import com.nast.yuni.request.AtivosListRequest;
import com.nast.yuni.response.AtivosResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public AtivosResponse obterAtivoPorId(Long id){
        Ativos ativo = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ativo não encontrado com ID: " + id));

        return AtivosResponse.builder()
                .ativos(List.of(ativo))
                .build();
    }

    public AtivosResponse criarAtivo(AtivosRequest request){
        Ativos ativo = Ativos.builder()
                .nome(request.getNome())
                .tipo(request.getTipo())
                .valorAtual(request.getValorAtual())
                .build();

        Ativos ativoSalvo = repository.save(ativo);

        return AtivosResponse.builder()
                .ativos(List.of(ativoSalvo))
                .build();
    }

    public AtivosResponse criarAtivosLote(AtivosListRequest request){
        List<Ativos> ativos = request.getAtivos()
                .stream()
                .map(ativoRequest -> Ativos.builder()
                        .nome(ativoRequest.getNome())
                        .tipo(ativoRequest.getTipo())
                        .valorAtual(ativoRequest.getValorAtual())
                        .build())
                .collect(Collectors.toList());

        List<Ativos> ativosSalvos = repository.saveAll(ativos);

        return AtivosResponse.builder()
                .ativos(ativosSalvos)
                .build();
    }

    public AtivosResponse atualizarAtivo(Long id, AtivosRequest request){
        Ativos ativoExistente = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ativo não encontrado com ID: " + id));

        ativoExistente = Ativos.builder()
                .id(id)
                .nome(request.getNome())
                .tipo(request.getTipo())
                .valorAtual(request.getValorAtual())
                .build();

        Ativos ativoAtualizado = repository.save(ativoExistente);

        return AtivosResponse.builder()
                .ativos(List.of(ativoAtualizado))
                .build();
    }

    public void deletarAtivo(Long id){
        Ativos ativo = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ativo não encontrado com ID: " + id));

        repository.deleteById(id);
    }
}
