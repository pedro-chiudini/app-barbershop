package com.pedrochiudini.app_barbershop.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pedrochiudini.app_barbershop.dto.TimetableResponseDTO;
import com.pedrochiudini.app_barbershop.dto.ClientSchedulingResponseDTO;
import com.pedrochiudini.app_barbershop.dto.DateRequestDTO;
import com.pedrochiudini.app_barbershop.dto.SchedulingRequestDTO;
import com.pedrochiudini.app_barbershop.dto.SchedulingResponseDTO;
import com.pedrochiudini.app_barbershop.exception.SchedulingNotFoundException;
import com.pedrochiudini.app_barbershop.exception.ServiceNotFoundException;
import com.pedrochiudini.app_barbershop.modelDomain.Scheduling;
import com.pedrochiudini.app_barbershop.repository.SchedulingRepository;
import com.pedrochiudini.app_barbershop.service.SchedulingService;

@RestController
@RequestMapping("/api/schedules")
public class SchedulingController {

    @Autowired
    private SchedulingRepository schedulingRepository;

    @Autowired
    private SchedulingService schedulingService;

    @GetMapping("/available-schedules")
    public ResponseEntity<?> getAvailableSchedules(@RequestParam("date") String date) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate newDate = LocalDate.parse(date, formatter);

            DateRequestDTO dateRequest = new DateRequestDTO(newDate);

            List<TimetableResponseDTO> availableSchedulesList = schedulingService
                    .findAvailableSchedulesByDate(dateRequest);
            return ResponseEntity.ok(availableSchedulesList);
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().body("Formato de data inválido.");
        }
    }

    @GetMapping
    public ResponseEntity<List<SchedulingResponseDTO>> getAll() {
        try {
            List<SchedulingResponseDTO> schedulingList = schedulingRepository.findAll().stream().map(SchedulingResponseDTO::new).toList();
            return ResponseEntity.ok(schedulingList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<ClientSchedulingResponseDTO>> getAllClientByID(@PathVariable Long id) {
        try {
            schedulingService.updateExpiredSchedulings();
            List<ClientSchedulingResponseDTO> schedulingList = schedulingRepository.findAllByClientId(id);
            return ResponseEntity.ok(schedulingList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<SchedulingResponseDTO> getSchedulingByID(@PathVariable Long id) {
        try {
            Optional<Scheduling> scheduling = schedulingRepository.findById(id);
            if (scheduling.isPresent()) {
                SchedulingResponseDTO responseDTO = new SchedulingResponseDTO(scheduling.get());
                return ResponseEntity.ok(responseDTO);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    private ResponseEntity<?> saveScheduling(@RequestBody SchedulingRequestDTO data) {
        try {
            Scheduling scheduling = schedulingService.createScheduling(data);
            return ResponseEntity.status(HttpStatus.CREATED).body(scheduling.getId());
        } catch (ServiceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating schedule");
        }
    }

    @PutMapping("/cancel/{id}")
    public ResponseEntity<?> cancelScheduling(@PathVariable Long id) {
        try {
            SchedulingResponseDTO scheduling = schedulingService.cancelScheduling(id);
            return ResponseEntity.ok(scheduling);
        } catch (SchedulingNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error canceling scheduling");
        }
    }

}
