package com.pedrochiudini.app_barbershop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pedrochiudini.app_barbershop.dto.SchedulingRequestDTO;
import com.pedrochiudini.app_barbershop.dto.SchedulingResponseDTO;
import com.pedrochiudini.app_barbershop.modelDomain.Scheduling;
import com.pedrochiudini.app_barbershop.repository.SchedulingRepository;

@RestController
@RequestMapping("/c/schedules")
public class SchedulingController {
    
    @Autowired
    private SchedulingRepository schedulingRepository;
    
    @GetMapping
    public ResponseEntity<List<SchedulingResponseDTO>> getAll() {
        try {
            List<SchedulingResponseDTO> schedulingList = schedulingRepository.findAll().stream().map(SchedulingResponseDTO::new).toList();
            return ResponseEntity.ok(schedulingList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<SchedulingResponseDTO>> getAllClientByID(@PathVariable Long id) {
        try {
            List<SchedulingResponseDTO> schedulingList = schedulingRepository.findAllByClientId(id);
            return ResponseEntity.ok(schedulingList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    private ResponseEntity<HttpStatus> saveScheduling(@RequestBody SchedulingRequestDTO data) {
        try {
            Scheduling schedulingData = new Scheduling(data);
            schedulingRepository.save(schedulingData);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(null);
        }
    }

}
