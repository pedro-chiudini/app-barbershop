package com.pedrochiudini.app_barbershop.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.pedrochiudini.app_barbershop.dto.SchedulingRequestDTO;
import com.pedrochiudini.app_barbershop.exception.SchedulingNotFoundException;
import com.pedrochiudini.app_barbershop.exception.ServiceNotFoundException;
import com.pedrochiudini.app_barbershop.modelDomain.Scheduling;
import com.pedrochiudini.app_barbershop.modelDomain.Service;
import com.pedrochiudini.app_barbershop.repository.SchedulingRepository;
import com.pedrochiudini.app_barbershop.repository.ServiceRepository;
import com.pedrochiudini.app_barbershop.util.StatusSchedules;

@org.springframework.stereotype.Service
public class SchedulingService {

    @Autowired
    private SchedulingRepository schedulingRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    public Scheduling createScheduling(SchedulingRequestDTO data) {
        Service service = (Service) serviceRepository.findById(data.service().getId())
                .orElseThrow(() -> new ServiceNotFoundException("Service not found"));

        Scheduling scheduling = new Scheduling(data);
        scheduling.setPrice(service.getPrice());
        scheduling.setStatus(StatusSchedules.CONFIRMADO);

        return schedulingRepository.save(scheduling);
    }

    public Scheduling cancelScheduling(Long schedulingId) {
        Scheduling scheduling = schedulingRepository.findById(schedulingId)
                .orElseThrow(() -> new SchedulingNotFoundException("Scheduling not found"));

        scheduling.setStatus(StatusSchedules.CANCELADO);

        return schedulingRepository.save(scheduling);
    }

}
