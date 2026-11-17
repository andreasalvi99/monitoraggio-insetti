package it.monitoraggioinsetti.monitoraggio_insetti.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import it.monitoraggioinsetti.monitoraggio_insetti.dto.request.NewReportRequest;
import it.monitoraggioinsetti.monitoraggio_insetti.dto.request.ReportStatusRequest;
import it.monitoraggioinsetti.monitoraggio_insetti.dto.response.NewReportResponse;
import it.monitoraggioinsetti.monitoraggio_insetti.dto.response.ReportStatusResponse;
import it.monitoraggioinsetti.monitoraggio_insetti.model.Report;
import it.monitoraggioinsetti.monitoraggio_insetti.service.ReportService;

@RestController
public class ReportController {
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping("/api/segnalazioni")
    public ResponseEntity<NewReportResponse> create(@RequestBody NewReportRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.reportService.create(request.latitude(), request.longitude(), request.reportedInsectId(),
                        request.municipalityId(), request.reporterEmail(), request.description()));
    }

    @PostMapping("/api/segnalazioni/stato")
    public ResponseEntity<ReportStatusResponse> findStatus(@RequestBody ReportStatusRequest request) {
        Optional<Report> result = this.reportService.findByTrackingcode(request.trackingCode());

        if (result.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Report report = result.get();
        ReportStatusResponse response = new ReportStatusResponse(report.getId(), report.getStatus());
        return ResponseEntity.ok(response);
    }
}
