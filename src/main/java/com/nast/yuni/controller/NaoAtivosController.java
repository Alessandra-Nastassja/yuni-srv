package com.nast.yuni.controller;

import com.nast.yuni.request.NaoAtivosRequest;
import com.nast.yuni.response.NaoAtivosResponse;
import com.nast.yuni.service.NaoAtivosService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/nao-ativos")
@RequiredArgsConstructor
public class NaoAtivosController {

    private final NaoAtivosService service;

    @GetMapping
    public ResponseEntity<NaoAtivosResponse> listarNaoAtivos() {
        return ResponseEntity.ok(service.listarNaoAtivos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<NaoAtivosResponse> obterNaoAtivoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obterNaoAtivoPorId(id));
    }

    @PostMapping
    public ResponseEntity<NaoAtivosResponse> criarNaoAtivo(@RequestBody NaoAtivosRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criarNaoAtivo(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NaoAtivosResponse> atualizarNaoAtivo(
            @PathVariable Long id,
            @RequestBody NaoAtivosRequest request) {
        return ResponseEntity.ok(service.atualizarNaoAtivo(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarNaoAtivo(@PathVariable Long id) {
        service.deletarNaoAtivo(id);
        return ResponseEntity.noContent().build();
    }
}

