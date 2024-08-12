package com.pedrochiudini.app_barbershop.dto;

import com.pedrochiudini.app_barbershop.modelDomain.Barber;

public record BarberResponseDTO(Long id, String name, String telephone, String cpf, String email) {
    
    public BarberResponseDTO(Barber barber) {
        this(barber.getId(), barber.getName(), barber.getTelephone(), barber.getCpf(), barber.getEmail());
    }

}
