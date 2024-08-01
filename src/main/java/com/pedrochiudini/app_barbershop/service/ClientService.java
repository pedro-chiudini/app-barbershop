package com.pedrochiudini.app_barbershop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.pedrochiudini.app_barbershop.dto.LoginDTO;
import com.pedrochiudini.app_barbershop.dto.LoginResponseDTO;
import com.pedrochiudini.app_barbershop.dto.RegisterDTO;
import com.pedrochiudini.app_barbershop.modelDomain.Client;
import com.pedrochiudini.app_barbershop.repository.ClientRepository;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    public String register(RegisterDTO data) {
        if (this.clientRepository.findByUsername(data.username()) != null) {
            return "The username already exists.";
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        Client newClient = new Client(data.name(), data.username(), encryptedPassword, data.telephone());

        this.clientRepository.save(newClient);

        return "Registered user!";
    }

    public LoginResponseDTO login(LoginDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.username(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((Client) auth.getPrincipal());

        return new LoginResponseDTO(token);
    }

}
