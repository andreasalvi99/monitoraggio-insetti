package it.monitoraggioinsetti.monitoraggio_insetti.service;

import org.springframework.stereotype.Service;

import it.monitoraggioinsetti.monitoraggio_insetti.repository.InsectRepository;

@Service 
public class InsectService {
    private final InsectRepository insectRepository;

    public InsectService(InsectRepository insectRepository) {
        this.insectRepository = insectRepository;
    }
}
