package it.monitoraggioinsetti.monitoraggio_insetti.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import it.monitoraggioinsetti.monitoraggio_insetti.dto.request.NewReportRequest;
import it.monitoraggioinsetti.monitoraggio_insetti.dto.request.ReportStatusRequest;
import it.monitoraggioinsetti.monitoraggio_insetti.dto.response.NewReportResponse;
import it.monitoraggioinsetti.monitoraggio_insetti.dto.response.ReportStatusResponse;
import it.monitoraggioinsetti.monitoraggio_insetti.dto.response.ReportSummaryResponse;
import it.monitoraggioinsetti.monitoraggio_insetti.model.Report;
import it.monitoraggioinsetti.monitoraggio_insetti.service.ReportService;

@RestController
public class ReportController {
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    private ReportSummaryResponse convert(Report report) {
        ReportSummaryResponse result = new ReportSummaryResponse(report.getId(), report.getStatus(),
                report.getReportDate());
        return result;
    }

    @GetMapping("/api/segnalazioni")
    public ResponseEntity<List<ReportSummaryResponse>> showAll() {
        List<Report> reports = this.reportService.findAll();
        List<ReportSummaryResponse> reportsConverted = new ArrayList<>();
        for (Report report : reports) {
            reportsConverted.add(convert(report));
        }

        return ResponseEntity.ok(reportsConverted);
    }

    @GetMapping("/api/segnalazioni/{id}")
    public ResponseEntity<Report> findById(@PathVariable(name = "id") Long reportId) {
        return ResponseEntity.ok(this.reportService.findReport(reportId));
    }

    @PostMapping(value = "/api/segnalazioni", consumes = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NewReportResponse> create(@RequestBody NewReportRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.reportService.create(request.latitude(), request.longitude(), request.reportedInsectId(),
                        request.municipalityId(), request.reporterEmail(), request.description()));
    }

    @PostMapping(value = "/api/segnalazioni", consumes = org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<NewReportResponse> createWithPhotos(@RequestPart("report") NewReportRequest request,
            @RequestPart(value = "photos", required = false) List<MultipartFile> photos) {
        NewReportResponse response = this.reportService.create(request.latitude(), request.longitude(),
                request.reportedInsectId(), request.municipalityId(), request.reporterEmail(), request.description(),
                photos);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
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
