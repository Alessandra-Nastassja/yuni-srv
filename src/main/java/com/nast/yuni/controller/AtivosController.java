package com.nast.yuni.controller;

import com.nast.yuni.request.AtivosRequest;
import com.nast.yuni.response.AtivosResponse;
import com.nast.yuni.service.AtivosService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ativos")
@RequiredArgsConstructor
public class AtivosController {

    private final AtivosService service;

    @GetMapping
    public AtivosResponse listarAtivos(){
        return service.listarAtivos();
    }

    @PostMapping
    public AtivosResponse criarAtivo(@RequestBody AtivosRequest request){
        return service.criarAtivo(request);
    }
}
