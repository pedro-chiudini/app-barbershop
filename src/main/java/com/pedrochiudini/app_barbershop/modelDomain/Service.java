package com.pedrochiudini.app_barbershop.modelDomain;

import java.math.BigDecimal;

import com.pedrochiudini.app_barbershop.dto.ServiceRequestDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_services")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Service {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 15, nullable = false)
    private String name;
    
    @Column(nullable = false, precision = 6, scale = 2)
    private BigDecimal price;
    
    public Service(ServiceRequestDTO data) {
        this.name = data.name();
        this.price = data.price();
    }
    
}
