package com.pedrochiudini.app_barbershop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pedrochiudini.app_barbershop.dto.SchedulingRequestDTO;
import com.pedrochiudini.app_barbershop.dto.SchedulingResponseDTO;
import com.pedrochiudini.app_barbershop.exception.SchedulingNotFoundException;
import com.pedrochiudini.app_barbershop.exception.ServiceNotFoundException;
import com.pedrochiudini.app_barbershop.modelDomain.Scheduling;
import com.pedrochiudini.app_barbershop.repository.SchedulingRepository;
import com.pedrochiudini.app_barbershop.service.SchedulingService;

@RestController
@RequestMapping("/api/schedules")
public class SchedulingController {
    
    @Autowired
    private SchedulingRepository schedulingRepository;

    @Autowired
    private SchedulingService schedulingService;
    
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
    private ResponseEntity<?> saveScheduling(@RequestBody SchedulingRequestDTO data) {
        try {
            schedulingService.createScheduling(data);
            return ResponseEntity.status(HttpStatus.CREATED).build(); 
        } catch (ServiceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating schedule");
        }
    }

    @PutMapping("/cancel/{id}")
    public ResponseEntity<?> cancelScheduling(@PathVariable Long id) {
        try {
            Scheduling scheduling = schedulingService.cancelScheduling(id);
            return ResponseEntity.ok(scheduling);
        } catch (SchedulingNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error canceling scheduling");
        }
    }

}
