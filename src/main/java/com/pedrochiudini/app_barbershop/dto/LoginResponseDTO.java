package com.pedrochiudini.app_barbershop.dto;

import com.pedrochiudini.app_barbershop.modelDomain.Client;

public record LoginResponseDTO(String token, Client client) {
    
}
