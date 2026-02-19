package com.nast.yuni.controller;

import com.nast.yuni.request.MetasRequest;
import com.nast.yuni.response.MetasResponse;
import com.nast.yuni.service.MetasService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/metas")
@RequiredArgsConstructor
public class MetasController {

    private final MetasService service;

    @GetMapping
    public MetasResponse listarMetas(){
        return service.listarMetas();
    }

    @PostMapping
    public MetasResponse criarMeta(@RequestBody MetasRequest request){
        return service.criarMeta(request);
    }
}
