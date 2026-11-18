package it.monitoraggioinsetti.monitoraggio_insetti.service;

import org.springframework.stereotype.Service;

import it.monitoraggioinsetti.monitoraggio_insetti.error.ReportNotFoundException;
import it.monitoraggioinsetti.monitoraggio_insetti.error.UserNotFoundException;
import it.monitoraggioinsetti.monitoraggio_insetti.model.Report;
import it.monitoraggioinsetti.monitoraggio_insetti.model.User;
import it.monitoraggioinsetti.monitoraggio_insetti.repository.CaseRepository;
import it.monitoraggioinsetti.monitoraggio_insetti.repository.ReportRepository;
import it.monitoraggioinsetti.monitoraggio_insetti.repository.UserRepository;

@Service
public class CaseService {
    private final CaseRepository caseRepository;
    private final ReportRepository reportRepository;
    private final UserRepository userRepository;

    public CaseService(CaseRepository caseRepository, ReportRepository reportRepository,
            UserRepository userRepository) {
        this.caseRepository = caseRepository;
        this.reportRepository = reportRepository;
        this.userRepository = userRepository;
    }

    private User findOperator(Long operatorId) {
        if (operatorId == null)
            throw new IllegalArgumentException("L'ID dell'operatore è obbgliatorio");
        return this.userRepository.findById(operatorId).orElseThrow(() -> new UserNotFoundException(operatorId));
    }

    private Report findReport(Long reportId) {
        if (reportId == null)
            throw new IllegalArgumentException("L'ID della segnalazione è obbligatorio");
        return this.reportRepository.findById(reportId).orElseThrow(() -> new ReportNotFoundException(reportId));
    }
}
