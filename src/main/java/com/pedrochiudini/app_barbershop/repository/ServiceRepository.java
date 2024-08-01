package com.pedrochiudini.app_barbershop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pedrochiudini.app_barbershop.modelDomain.Service;

public interface ServiceRepository extends JpaRepository<Service, Long> {
    
}
