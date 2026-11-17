package it.monitoraggioinsetti.monitoraggio_insetti.service;

import java.util.List;

import org.springframework.stereotype.Service;

import it.monitoraggioinsetti.monitoraggio_insetti.model.Insect;
import it.monitoraggioinsetti.monitoraggio_insetti.repository.InsectRepository;

@Service
public class InsectService {
    private final InsectRepository insectRepository;

    public InsectService(InsectRepository insectRepository) {
        this.insectRepository = insectRepository;
    }

    public List<Insect> findAll() {
        return this.insectRepository.findAll();
    }
}
