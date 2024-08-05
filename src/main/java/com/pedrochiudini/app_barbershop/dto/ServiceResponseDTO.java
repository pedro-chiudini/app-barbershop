package com.pedrochiudini.app_barbershop.dto;

import java.math.BigDecimal;

import com.pedrochiudini.app_barbershop.modelDomain.Service;

public record ServiceResponseDTO(Long id, String name, BigDecimal price) {
    
    public ServiceResponseDTO(Service service) {
        this(service.getId(), service.getName(), service.getPrice());
    }

}
