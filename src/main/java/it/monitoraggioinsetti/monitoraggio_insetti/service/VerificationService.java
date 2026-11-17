package it.monitoraggioinsetti.monitoraggio_insetti.service;

import org.springframework.stereotype.Service;

import it.monitoraggioinsetti.monitoraggio_insetti.error.InsectNotFoundException;
import it.monitoraggioinsetti.monitoraggio_insetti.error.ReportNotFoundException;
import it.monitoraggioinsetti.monitoraggio_insetti.model.Insect;
import it.monitoraggioinsetti.monitoraggio_insetti.model.Report;
import it.monitoraggioinsetti.monitoraggio_insetti.repository.InsectRepository;
import it.monitoraggioinsetti.monitoraggio_insetti.repository.ReportRepository;
import it.monitoraggioinsetti.monitoraggio_insetti.repository.VerificationRepository;

@Service
public class VerificationService {
    private final VerificationRepository verificationRepository;
    private final InsectRepository insectRepository;
    private final ReportRepository reportRepository;

    public VerificationService(VerificationRepository verificationRepository, InsectRepository insectRepository,
            ReportRepository reportRepository) {
        this.verificationRepository = verificationRepository;
        this.insectRepository = insectRepository;
        this.reportRepository = reportRepository;
    }

    private Insect findInsect(Long insectId) {
        return this.insectRepository.findById(insectId).orElseThrow(() -> new InsectNotFoundException(insectId));
    }

    private Report findReport(Long reportId) {
        return this.reportRepository.findById(reportId).orElseThrow(() -> new ReportNotFoundException(reportId));
    }
}
