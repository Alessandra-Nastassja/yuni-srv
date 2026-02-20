package com.nast.yuni.controller;

import com.nast.yuni.request.AtivosRequest;
import com.nast.yuni.request.AtivosListRequest;
import com.nast.yuni.response.AtivosResponse;
import com.nast.yuni.service.AtivosService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ativos")
@RequiredArgsConstructor
public class AtivosController {

    private final AtivosService service;

    @GetMapping
    public ResponseEntity<AtivosResponse> listarAtivos(){
        return ResponseEntity.ok(service.listarAtivos());
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
