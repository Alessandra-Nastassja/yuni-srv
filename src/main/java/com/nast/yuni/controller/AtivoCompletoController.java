package com.nast.yuni.controller;

import com.nast.yuni.domain.AtivosCompleto;
import com.nast.yuni.request.AtivosCompletoRequest;
import com.nast.yuni.response.AtivoCompletoResponse;
import com.nast.yuni.service.AtivosCompletoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/ativos/completo")
@RequiredArgsConstructor
public class AtivoCompletoController {

    private final AtivosCompletoService service;

    @GetMapping
    public ResponseEntity<List<AtivoCompletoResponse>> listarAtivosCompleto() {
        List<AtivoCompletoResponse> resposta = service.listarAtivosCompleto()
                .stream()
                .map(AtivoCompletoResponse::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(resposta);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AtivoCompletoResponse> obterAtivoCompleto(@PathVariable Long id) {
        AtivosCompleto ativo = service.obterAtivoCompletoPorId(id);
        return ResponseEntity.ok(AtivoCompletoResponse.fromEntity(ativo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAtivoCompleto(@PathVariable Long id) {
        service.deletarAtivoCompleto(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<AtivoCompletoResponse> criarAtivo(
            @Valid @RequestBody AtivosCompletoRequest request) {
        try {
            AtivosCompleto ativo = service.criarAtivo(request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(AtivoCompletoResponse.fromEntity(ativo));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Erro ao criar ativo: " + e.getMessage());
        }
    }
}
