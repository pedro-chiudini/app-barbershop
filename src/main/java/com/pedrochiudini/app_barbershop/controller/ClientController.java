package com.pedrochiudini.app_barbershop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pedrochiudini.app_barbershop.dto.LoginDTO;
import com.pedrochiudini.app_barbershop.dto.LoginResponseDTO;
import com.pedrochiudini.app_barbershop.dto.RegisterDTO;
import com.pedrochiudini.app_barbershop.service.ClientService;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping("/register")
    public ResponseEntity<String> registerClient(@RequestBody RegisterDTO data) {
        try {
            String response = clientService.register(data);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.toString());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> loginClient(@RequestBody LoginDTO data) {
        try {
            LoginResponseDTO responseDTO = clientService.login(data);
            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

}
