package com.pedrochiudini.app_barbershop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pedrochiudini.app_barbershop.dto.ClientSchedulingResponseDTO;
import com.pedrochiudini.app_barbershop.modelDomain.Scheduling;

public interface SchedulingRepository extends JpaRepository<Scheduling, Long> {
    
    List<ClientSchedulingResponseDTO> findAllByClientId(Long clientId);

}
