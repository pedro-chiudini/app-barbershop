package com.pedrochiudini.app_barbershop.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pedrochiudini.app_barbershop.dto.ClientSchedulingResponseDTO;
import com.pedrochiudini.app_barbershop.modelDomain.Scheduling;
import com.pedrochiudini.app_barbershop.util.StatusSchedules;

public interface SchedulingRepository extends JpaRepository<Scheduling, Long> {

    List<Scheduling> findAllByDate(LocalDate date);
    
    List<ClientSchedulingResponseDTO> findAllByClientId(Long clientId);

    List<Scheduling> findByStatusAndDateAndTimeBefore(StatusSchedules status, LocalDate date, LocalTime time);

}
