package com.pedrochiudini.app_barbershop.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.pedrochiudini.app_barbershop.dto.DateRequestDTO;
import com.pedrochiudini.app_barbershop.dto.SchedulingRequestDTO;
import com.pedrochiudini.app_barbershop.dto.TimetableResponseDTO;
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

    private final List<LocalTime> availableHours = List.of(
            LocalTime.of(9, 0),
            LocalTime.of(9, 30),
            LocalTime.of(10, 0),
            LocalTime.of(10, 30),
            LocalTime.of(11, 0),
            LocalTime.of(11, 30),
            LocalTime.of(13, 0),
            LocalTime.of(13, 30),
            LocalTime.of(14, 0),
            LocalTime.of(14, 30),
            LocalTime.of(15, 0),
            LocalTime.of(15, 30),
            LocalTime.of(16, 0),
            LocalTime.of(16, 30),
            LocalTime.of(17, 0));

    public List<TimetableResponseDTO> findAvailableSchedulesByDate(DateRequestDTO date) {
        LocalDate requestDate = date.date();
    
        if (requestDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
                return Collections.emptyList();
        }

        List<Scheduling> schedulesOnDate = schedulingRepository.findAllByDate(date.date());

        List<LocalTime> scheduledTimes = schedulesOnDate.stream()
                .map(Scheduling::getTime)
                .collect(Collectors.toList());

        List<LocalTime> availableSchedules = availableHours.stream()
                .filter(hour -> !scheduledTimes.contains(hour))
                .collect(Collectors.toList());

        return availableSchedules.stream()
                .map(TimetableResponseDTO::new)
                .collect(Collectors.toList());
    }

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

    public void updateExpiredSchedulings() {
        LocalDate nowDate = LocalDate.now();
        ZoneId zoneId = ZoneId.of("America/Sao_Paulo");
        LocalTime nowTime = LocalTime.now(zoneId);

        List<Scheduling> previousDateSchedulings = schedulingRepository.findByStatusAndDateBefore(StatusSchedules.CONFIRMADO, nowDate);

        List<Scheduling> todaySchedulings = schedulingRepository.findByStatusAndDateAndTimeBefore(StatusSchedules.CONFIRMADO, nowDate, nowTime);

        List<Scheduling> allExpiredSchedulings = new ArrayList<>();
        allExpiredSchedulings.addAll(previousDateSchedulings);
        allExpiredSchedulings.addAll(todaySchedulings);

        allExpiredSchedulings.forEach(scheduling -> {
                scheduling.setStatus(StatusSchedules.CONCLUIDO);
                schedulingRepository.save(scheduling);
        });
    }

}
