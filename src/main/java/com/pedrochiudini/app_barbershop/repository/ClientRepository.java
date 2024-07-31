package com.pedrochiudini.app_barbershop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.pedrochiudini.app_barbershop.modelDomain.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
    
    boolean existsByUsername(String username);

    UserDetails findByUsername(String username);
    
}
