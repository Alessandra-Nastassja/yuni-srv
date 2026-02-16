package com.nast.yuni.controller;

import com.nast.yuni.response.MetasResponse;
import com.nast.yuni.service.MetasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/metas")
public class MetasController {

    @Autowired
    private MetasService service;

    @GetMapping
    public MetasResponse metas(){
        return service.metas();
    }

}
