package it.monitoraggioinsetti.monitoraggio_insetti.service;

import java.util.List;

import org.springframework.stereotype.Service;

import it.monitoraggioinsetti.monitoraggio_insetti.model.Municipality;
import it.monitoraggioinsetti.monitoraggio_insetti.repository.MunicipalityRepository;

@Service
public class MunicipalityService {
    private final MunicipalityRepository municipalityRepository;

    public MunicipalityService(MunicipalityRepository municipalityRepository) {
        this.municipalityRepository = municipalityRepository;
    }

    public List<Municipality> findAll() {
        return this.municipalityRepository.findAll();
    }
}
