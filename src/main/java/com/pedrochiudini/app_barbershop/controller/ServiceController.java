package com.pedrochiudini.app_barbershop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pedrochiudini.app_barbershop.dto.ServiceRequestDTO;
import com.pedrochiudini.app_barbershop.dto.ServiceResponseDTO;
import com.pedrochiudini.app_barbershop.modelDomain.Service;
import com.pedrochiudini.app_barbershop.repository.ServiceRepository;

@RestController
@RequestMapping("/api/services")
public class ServiceController {
    
    @Autowired
    private ServiceRepository serviceRepository;

    @GetMapping
    public ResponseEntity<List<ServiceResponseDTO>> getAll() {
        try {
            List<ServiceResponseDTO> serviceList = serviceRepository.findAll().stream().map(ServiceResponseDTO::new).toList();
            return ResponseEntity.ok().body(serviceList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }       
    }

    @PostMapping
    private void saveService(@RequestBody ServiceRequestDTO data) {
        try {
            Service serviceData = new Service(data);
            serviceRepository.save(serviceData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
