package it.monitoraggioinsetti.monitoraggio_insetti.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import it.monitoraggioinsetti.monitoraggio_insetti.dto.NewReportRequest;
import it.monitoraggioinsetti.monitoraggio_insetti.dto.NewReportResponse;
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
}
