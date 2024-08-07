package com.pedrochiudini.app_barbershop.dto;

import java.util.Date;

import com.pedrochiudini.app_barbershop.modelDomain.Barber;
import com.pedrochiudini.app_barbershop.modelDomain.Client;
import com.pedrochiudini.app_barbershop.modelDomain.Service;

public record SchedulingRequestDTO(Date date, String time, Barber barber, Client client, Service service) {
    
}
