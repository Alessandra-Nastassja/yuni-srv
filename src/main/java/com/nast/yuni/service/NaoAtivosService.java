package com.nast.yuni.service;

import com.nast.yuni.domain.NaoAtivos;
import com.nast.yuni.repository.NaoAtivosRepository;
import com.nast.yuni.request.NaoAtivosRequest;
import com.nast.yuni.response.NaoAtivosResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NaoAtivosService {

    private final NaoAtivosRepository repository;

    public NaoAtivosResponse listarNaoAtivos() {
        List<NaoAtivos> naoAtivos = repository.findAllByOrderByValorAtualDesc();
        return NaoAtivosResponse.builder()
                .naoAtivos(naoAtivos)
                .build();
    }

    public NaoAtivosResponse obterNaoAtivoPorId(Long id) {
        NaoAtivos naoAtivo = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Não ativo não encontrado com ID: " + id));
        return NaoAtivosResponse.builder()
                .naoAtivos(List.of(naoAtivo))
                .build();
    }

    public NaoAtivosResponse criarNaoAtivo(NaoAtivosRequest request) {
        validarCampos(request);

        NaoAtivos naoAtivo = NaoAtivos.builder()
                .nome(request.getNome())
                .tipo(request.getTipo())
                .valorAtual(request.getValorAtual())
                .build();

        NaoAtivos naoAtivoSalvo = repository.save(naoAtivo);

        return NaoAtivosResponse.builder()
                .naoAtivos(List.of(naoAtivoSalvo))
                .build();
    }

    public NaoAtivosResponse atualizarNaoAtivo(Long id, NaoAtivosRequest request) {
        NaoAtivos naoAtivoExistente = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Não ativo não encontrado com ID: " + id));

        validarCampos(request);

        NaoAtivos naoAtivoAtualizado = NaoAtivos.builder()
                .id(id)
                .nome(request.getNome())
                .tipo(request.getTipo())
                .valorAtual(request.getValorAtual())
                .build();

        NaoAtivos salvo = repository.save(naoAtivoAtualizado);

        return NaoAtivosResponse.builder()
                .naoAtivos(List.of(salvo))
                .build();
    }

    public void deletarNaoAtivo(Long id) {
        NaoAtivos naoAtivo = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Não ativo não encontrado com ID: " + id));
        repository.deleteById(id);
    }

    private void validarCampos(NaoAtivosRequest request) {
        // Nome é obrigatório apenas quando tipo não for "fgts"
        if (!"fgts".equals(request.getTipo())) {
            if (request.getNome() == null || request.getNome().isBlank()) {
                throw new IllegalArgumentException("Nome é obrigatório para tipos diferentes de FGTS");
            }
        }

        // Tipo é sempre obrigatório
        if (request.getTipo() == null || request.getTipo().isBlank()) {
            throw new IllegalArgumentException("Tipo é obrigatório");
        }
    }
}
