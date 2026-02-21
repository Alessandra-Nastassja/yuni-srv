package com.nast.yuni.controller;

import com.nast.yuni.request.AtivosRequest;
import com.nast.yuni.request.AtivosListRequest;
import com.nast.yuni.response.AtivosResponse;
import com.nast.yuni.response.AtivoCompletoResponse;
import com.nast.yuni.response.AtivoResumoResponse;
import com.nast.yuni.service.AtivosService;
import com.nast.yuni.service.AtivosCompletoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/ativos")
@RequiredArgsConstructor
public class AtivosController {

    private final AtivosService service;
    private final AtivosCompletoService ativosCompletoService;

    @GetMapping
    public ResponseEntity<AtivosResponse> listarAtivos(){
        AtivosResponse response = service.listarAtivos();

        List<AtivoResumoResponse> unidos = new ArrayList<>(response.getAtivos());

        unidos.addAll(ativosCompletoService.listarAtivosCompleto()
                .stream()
                .map(AtivoResumoResponse::fromAtivoCompleto)
                .collect(Collectors.toList()));
        response.setAtivos(unidos);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AtivosResponse> obterAtivoPorId(@PathVariable Long id){
        return ResponseEntity.ok(service.obterAtivoPorId(id));
    }

    @PostMapping
    public ResponseEntity<AtivosResponse> criarAtivo(@RequestBody AtivosRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criarAtivo(request));
    }

    @PostMapping("/lote")
    public ResponseEntity<AtivosResponse> criarAtivosLote(@RequestBody AtivosListRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criarAtivosLote(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AtivosResponse> atualizarAtivo(@PathVariable Long id, @RequestBody AtivosRequest request){
        return ResponseEntity.ok(service.atualizarAtivo(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAtivo(@PathVariable Long id){
        service.deletarAtivo(id);
        return ResponseEntity.noContent().build();
    }
}
