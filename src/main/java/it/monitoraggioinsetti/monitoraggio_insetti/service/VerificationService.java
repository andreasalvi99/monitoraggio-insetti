package it.monitoraggioinsetti.monitoraggio_insetti.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.monitoraggioinsetti.monitoraggio_insetti.error.InsectNotFoundException;
import it.monitoraggioinsetti.monitoraggio_insetti.error.OperationNotAllowedException;
import it.monitoraggioinsetti.monitoraggio_insetti.error.ReportNotFoundException;
import it.monitoraggioinsetti.monitoraggio_insetti.error.UserNotFoundException;
import it.monitoraggioinsetti.monitoraggio_insetti.model.Insect;
import it.monitoraggioinsetti.monitoraggio_insetti.model.Report;
import it.monitoraggioinsetti.monitoraggio_insetti.model.User;
import it.monitoraggioinsetti.monitoraggio_insetti.model.Verification;
import it.monitoraggioinsetti.monitoraggio_insetti.repository.InsectRepository;
import it.monitoraggioinsetti.monitoraggio_insetti.repository.ReportRepository;
import it.monitoraggioinsetti.monitoraggio_insetti.repository.UserRepository;
import it.monitoraggioinsetti.monitoraggio_insetti.repository.VerificationRepository;

@Service
public class VerificationService {
    private final VerificationRepository verificationRepository;
    private final InsectRepository insectRepository;
    private final ReportRepository reportRepository;
    private final UserRepository userRepository;

    public VerificationService(VerificationRepository verificationRepository, InsectRepository insectRepository,
            ReportRepository reportRepository, UserRepository userRepository) {
        this.verificationRepository = verificationRepository;
        this.insectRepository = insectRepository;
        this.reportRepository = reportRepository;
        this.userRepository = userRepository;
    }

    private Insect findInsect(Long insectId) {
        return this.insectRepository.findById(insectId).orElseThrow(() -> new InsectNotFoundException(insectId));
    }

    private Report findReport(Long reportId) {
        if (reportId == null)
            throw new IllegalArgumentException("ID segnalazione non valido");
        return this.reportRepository.findById(reportId).orElseThrow(() -> new ReportNotFoundException(reportId));
    }

    private User findUser(Long userId) {
        if (userId == null)
            throw new IllegalArgumentException("ID utente non valido");
        return this.userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
    }

    @Transactional
    public Verification create(Long reportId, Long confirmedInsectId, Long operatorId, String notes, String outcome) {
        Report toVerify = findReport(reportId);
        User operator = findUser(operatorId);

        if (!operator.isActive())
            throw new OperationNotAllowedException("L'utente è disattivato");
        if (!"OPERATORE".equals(operator.getRole()))
            throw new OperationNotAllowedException("L'utente non è autorizzato");

        if ("CONFERMATA".equals(outcome)) {

            if (confirmedInsectId == null)
                throw new IllegalArgumentException("L'insetto accertato è pbbligatorio");

            Insect confirmedInsect = findInsect(confirmedInsectId);
            toVerify.confirm(confirmedInsect);

        } else if ("RESPINTA".equals(outcome)) {

            if (confirmedInsectId != null)
                throw new IllegalArgumentException("Una verifica respinta non può indicare un insetto accertato");

            toVerify.reject();

        } else {
            throw new IllegalArgumentException("Esito non valido");
        }

        Verification newVerification = new Verification(outcome, toVerify, operator, notes);
        return this.verificationRepository.save(newVerification);
    }
}
