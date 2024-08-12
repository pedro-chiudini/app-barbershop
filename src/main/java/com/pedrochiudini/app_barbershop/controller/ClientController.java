package com.pedrochiudini.app_barbershop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pedrochiudini.app_barbershop.dto.LoginDTO;
import com.pedrochiudini.app_barbershop.dto.LoginResponseDTO;
import com.pedrochiudini.app_barbershop.dto.RegisterDTO;
import com.pedrochiudini.app_barbershop.exception.InvalidCredentialsException;
import com.pedrochiudini.app_barbershop.exception.UsernameAlreadyExistsException;
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
            if (response.equals("The username already exists.")) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
            } else if (response.equals("Registered user!")) {
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            }
        } catch (UsernameAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
        return null;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> loginClient(@RequestBody LoginDTO data) {
        try {
            LoginResponseDTO responseDTO = clientService.login(data);
            return ResponseEntity.ok(responseDTO);
        } catch (InvalidCredentialsException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

}
