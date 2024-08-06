package com.pedrochiudini.app_barbershop.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pedrochiudini.app_barbershop.service.TokenService;

@RestController
@RequestMapping("/api/tokens/verify-token")
public class TokenController {

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public boolean verify(@RequestBody String token) {
        boolean response = tokenService.verifyToken(token);
        return response;
    }

}
