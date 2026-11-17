package it.monitoraggioinsetti.monitoraggio_insetti.service;

import org.springframework.stereotype.Service;

import it.monitoraggioinsetti.monitoraggio_insetti.repository.MunicipalityRepository;

@Service
public class MunicipalityService {
    private final MunicipalityRepository municipalityRepository;

    public MunicipalityService(MunicipalityRepository municipalityRepository) {
        this.municipalityRepository = municipalityRepository;
    }
}
