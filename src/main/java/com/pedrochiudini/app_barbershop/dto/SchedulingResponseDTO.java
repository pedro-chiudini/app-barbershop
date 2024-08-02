package com.pedrochiudini.app_barbershop.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.pedrochiudini.app_barbershop.modelDomain.Barber;
import com.pedrochiudini.app_barbershop.modelDomain.Client;
import com.pedrochiudini.app_barbershop.modelDomain.Scheduling;
import com.pedrochiudini.app_barbershop.modelDomain.Service;
import com.pedrochiudini.app_barbershop.util.StatusSchedules;

public record SchedulingResponseDTO(Long id, Date date, String time, BigDecimal price, StatusSchedules status, Barber barber, Client client, Service service) {
    
    public SchedulingResponseDTO(Scheduling scheduling) {
        this(scheduling.getId(), scheduling.getDate(), scheduling.getTime(), scheduling.getPrice(), scheduling.getStatus(), scheduling.getBarber(), scheduling.getClient(), scheduling.getService());
    }
}