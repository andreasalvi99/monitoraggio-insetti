package it.monitoraggioinsetti.monitoraggio_insetti.service;

import java.util.List;

import org.springframework.stereotype.Service;

import it.monitoraggioinsetti.monitoraggio_insetti.error.InsectNotFoundException;
import it.monitoraggioinsetti.monitoraggio_insetti.error.MunicipalityNotFoundException;
import it.monitoraggioinsetti.monitoraggio_insetti.model.Insect;
import it.monitoraggioinsetti.monitoraggio_insetti.model.Municipality;
import it.monitoraggioinsetti.monitoraggio_insetti.model.Report;
import it.monitoraggioinsetti.monitoraggio_insetti.repository.InsectRepository;
import it.monitoraggioinsetti.monitoraggio_insetti.repository.MunicipalityRepository;
import it.monitoraggioinsetti.monitoraggio_insetti.repository.ReportRepository;

@Service
public class ReportService {
    private final ReportRepository reportRepository;
    private final InsectRepository insectRepository;
    private final MunicipalityRepository municipalityRepository;

    public ReportService(ReportRepository reportRepository, InsectRepository insectRepository,
            MunicipalityRepository municipalityRepository) {
        this.reportRepository = reportRepository;
        this.insectRepository = insectRepository;
        this.municipalityRepository = municipalityRepository;
    }

    private Insect findInsect(Long insectId) {
        if (insectId == null)
            throw new IllegalArgumentException("ID insetto non valido");
        return this.insectRepository.findById(insectId).orElseThrow(() -> new InsectNotFoundException(insectId));
    }

    private Municipality findMunicipality(Long municipalityId) {
        if (municipalityId == null)
            throw new IllegalArgumentException("ID comune non valido");
        return this.municipalityRepository.findById(municipalityId)
                .orElseThrow(() -> new MunicipalityNotFoundException(municipalityId));
    }

    public List<Report> findAll() {
        return this.reportRepository.findAll();
    }
}
