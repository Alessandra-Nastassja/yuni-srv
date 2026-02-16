package com.nast.yuni.controller;

import com.nast.yuni.domain.ItemFinanceiro;
import com.nast.yuni.domain.PatrimonioAtivos;
import com.nast.yuni.response.PatrimonioResponse;
import com.nast.yuni.service.PatrimonioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/patrimonio")
public class PatrimonioController {

    @Autowired
    private PatrimonioService service;

    @GetMapping
    public PatrimonioResponse patrimonio(){
        return service.patrimonio();
    }
}

