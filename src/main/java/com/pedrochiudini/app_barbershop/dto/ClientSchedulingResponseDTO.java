package com.pedrochiudini.app_barbershop.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.pedrochiudini.app_barbershop.modelDomain.Scheduling;
import com.pedrochiudini.app_barbershop.util.StatusSchedules;

public record ClientSchedulingResponseDTO(Long id, LocalDate date, LocalTime time, StatusSchedules status) {
    
    public ClientSchedulingResponseDTO(Scheduling scheduling) {
        this(scheduling.getId(), scheduling.getDate(), scheduling.getTime(), scheduling.getStatus());
    }

}
