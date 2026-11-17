package it.monitoraggioinsetti.monitoraggio_insetti.service;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.HexFormat;
import java.util.List;

import org.springframework.stereotype.Service;

import it.monitoraggioinsetti.monitoraggio_insetti.dto.NewReportResponse;
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
    private final SecureRandom secureRandom = new SecureRandom();

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

    private String generateTrackingCode() {
        byte[] randomBytes = new byte[32];
        secureRandom.nextBytes(randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }

    private String hashTrackingCode(String trackingCode) {
        try {
            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
            byte[] digest = sha256.digest(trackingCode.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(digest);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 non disponibile", e);
        }
    }

    public List<Report> findAll() {
        return this.reportRepository.findAll();
    }

    public NewReportResponse create(BigDecimal latitude, BigDecimal longitude, Long reportedInsectId,
            Long municipalityId,
            String reporterEmail, String description) {
        Insect toFindInsect = findInsect(reportedInsectId);
        Municipality toFindMunicipality = findMunicipality(municipalityId);
        String trackingCode = generateTrackingCode();
        String trackingCodeHash = hashTrackingCode(trackingCode);
        Report newReport = new Report(trackingCodeHash, latitude, longitude, toFindInsect, toFindMunicipality,
                reporterEmail, description);
        Report savedReport = this.reportRepository.save(newReport);
        return new NewReportResponse(savedReport.getId(), trackingCode);
    }
}
