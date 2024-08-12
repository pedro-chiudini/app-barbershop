package com.pedrochiudini.app_barbershop.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.pedrochiudini.app_barbershop.modelDomain.Barber;
import com.pedrochiudini.app_barbershop.modelDomain.Client;
import com.pedrochiudini.app_barbershop.modelDomain.Service;

public record SchedulingRequestDTO(LocalDate date, LocalTime time, Barber barber, Client client, Service service) {
    
}
