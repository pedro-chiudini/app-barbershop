package com.pedrochiudini.app_barbershop.modelDomain;

import com.pedrochiudini.app_barbershop.dto.BarberRequestDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_barbers")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Barber extends User {

    @Column(length = 14, nullable = false, unique = true)
    private String cpf;

    @Column(length = 45, nullable = false)
    private String email;

    public Barber(BarberRequestDTO data) {
        super(data.name(), data.telephone());
        this.cpf = data.cpf();
        this.email = data.email();
    }

}
