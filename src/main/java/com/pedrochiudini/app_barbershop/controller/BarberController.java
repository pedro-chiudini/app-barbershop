package com.pedrochiudini.app_barbershop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pedrochiudini.app_barbershop.dto.BarberRequestDTO;
import com.pedrochiudini.app_barbershop.dto.BarberResponseDTO;
import com.pedrochiudini.app_barbershop.modelDomain.Barber;
import com.pedrochiudini.app_barbershop.repository.BarberRepository;

@RestController
@RequestMapping("/c/barbers")
public class BarberController {

    @Autowired
    private BarberRepository barberRepository;

    @GetMapping
    public ResponseEntity<List<BarberResponseDTO>> getAll() {
        try {
            List<BarberResponseDTO> barberList = barberRepository.findAll().stream().map(BarberResponseDTO::new).toList();
            return ResponseEntity.ok().body(barberList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }

    }

    @PostMapping
    private void saveBarber(@RequestBody BarberRequestDTO data) {
        try {
            Barber barberData = new Barber(data);
            barberRepository.save(barberData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
