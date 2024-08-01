package com.pedrochiudini.app_barbershop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pedrochiudini.app_barbershop.modelDomain.Barber;

public interface BarberRepository extends JpaRepository<Barber, Long> {
    
}
