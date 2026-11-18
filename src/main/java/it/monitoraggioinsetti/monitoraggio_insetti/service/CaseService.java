package it.monitoraggioinsetti.monitoraggio_insetti.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import it.monitoraggioinsetti.monitoraggio_insetti.error.OperationNotAllowedException;
import it.monitoraggioinsetti.monitoraggio_insetti.error.ReportNotFoundException;
import it.monitoraggioinsetti.monitoraggio_insetti.error.UserNotFoundException;
import it.monitoraggioinsetti.monitoraggio_insetti.model.Case;
import it.monitoraggioinsetti.monitoraggio_insetti.model.Insect;
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

    private GeographicCenter calculateCenter(List<Report> reports) {
        BigDecimal latitudeSum = BigDecimal.ZERO;
        BigDecimal longitudeSum = BigDecimal.ZERO;

        for (Report report : reports) {
            latitudeSum = latitudeSum.add(report.getLatitude());
            longitudeSum = longitudeSum.add(report.getLongitude());
        }

        BigDecimal count = BigDecimal.valueOf(reports.size());
        BigDecimal latitude = latitudeSum.divide(count, 7, RoundingMode.HALF_UP);
        BigDecimal longitude = longitudeSum.divide(count, 7, RoundingMode.HALF_UP);

        return new GeographicCenter(latitude, longitude);

    }

    private double calculateDistaceMeters(GeographicCenter center, Report report) {
        double centerLatitude = Math.toRadians(center.latitude().doubleValue());
        double centerLongitude = Math.toRadians(center.longitude().doubleValue());

        double reportLatitude = Math.toRadians(report.getLatitude().doubleValue());
        double reportLongitude = Math.toRadians(report.getLongitude().doubleValue());
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

    public List<Case> create(List<Long> reportIds, String description, Long operatorId) {
        if (reportIds == null || reportIds.size() < 2)
            throw new IllegalArgumentException("Le segnalazioni devono essere almeno due");

        Set<Long> ids = new HashSet<>(reportIds);
        if (ids.size() < reportIds.size())
            throw new IllegalArgumentException("La stessa segnalazione appare più volte");

        if (reportIds.contains(null))
            throw new IllegalArgumentException("GLi ID delle segnalazioni sono obbligatori");

        User operator = findOperator(operatorId);
        if (!operator.isActive())
            throw new OperationNotAllowedException("L'utente non è attivo");
        if (!"OPERATORE".equals(operator.getRole()))
            throw new OperationNotAllowedException("L'utente non è abilitato per questa operazione");

        Map<Long, List<Report>> reportsByInsectId = new HashMap<>();

        for (Long reportId : reportIds) {
            Report report = findReport(reportId);
            if (!"ACCERTATA".equals(report.getStatus()))
                throw new IllegalArgumentException(
                        "La segnalazione con ID " + reportId + " non è ancora stata confermata");
            if (report.getConfirmedInsect() == null)
                throw new IllegalArgumentException(
                        "Se l'insetto non è identificato non è possibile aprire una pratica");

            Long insectId = report.getConfirmedInsect().getId();

            if (!reportsByInsectId.containsKey(insectId)) {
                reportsByInsectId.put(insectId, new ArrayList<>());
            }

            reportsByInsectId.get(insectId).add(report);
        }

        int maxGroupSize = 0;

        for (List<Report> reportInMap : reportsByInsectId.values()) {
            if (reportInMap.size() > maxGroupSize)
                maxGroupSize = reportInMap.size();
        }

        if (maxGroupSize < 2)
            throw new IllegalArgumentException(
                    "Non ci sono abbastanza segnalazioni associate allo stesso insetto per aprire una pratica");

        for (List<Report> group : reportsByInsectId.values()) {
            if (group.size() == maxGroupSize) {
                GeographicCenter center = calculateCenter(group);
            }
        }

    }

}
