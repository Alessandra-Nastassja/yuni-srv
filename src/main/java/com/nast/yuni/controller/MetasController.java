package com.nast.yuni.controller;

import com.nast.yuni.request.MetasRequest;
import com.nast.yuni.response.MetasResponse;
import com.nast.yuni.service.MetasService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/metas")
@RequiredArgsConstructor
public class MetasController {

    private final MetasService service;

    @GetMapping
    public ResponseEntity<MetasResponse> listarMetas(){
        return ResponseEntity.ok(service.listarMetas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MetasResponse> obterMetaPorId(@PathVariable Long id){
        return ResponseEntity.ok(service.obterMetaPorId(id));
    }

    @PostMapping
    public ResponseEntity<MetasResponse> criarMeta(@RequestBody MetasRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criarMeta(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MetasResponse> atualizarMeta(@PathVariable Long id, @RequestBody MetasRequest request){
        return ResponseEntity.ok(service.atualizarMeta(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarMeta(@PathVariable Long id){
        service.deletarMeta(id);
        return ResponseEntity.noContent().build();
    }
}
