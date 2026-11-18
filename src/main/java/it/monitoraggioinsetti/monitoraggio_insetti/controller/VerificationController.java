package it.monitoraggioinsetti.monitoraggio_insetti.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import it.monitoraggioinsetti.monitoraggio_insetti.dto.request.NewVerificationRequest;
import it.monitoraggioinsetti.monitoraggio_insetti.dto.response.NewVerificationResponse;
import it.monitoraggioinsetti.monitoraggio_insetti.model.Verification;
import it.monitoraggioinsetti.monitoraggio_insetti.service.VerificationService;

@RestController
public class VerificationController {
    private final VerificationService verificationService;

    public VerificationController(VerificationService verificationService) {
        this.verificationService = verificationService;
    }

    private NewVerificationResponse convert(Verification verification) {
        NewVerificationResponse response = new NewVerificationResponse(verification.getOutcome(),
                verification.getNotes(), verification.getVerifiedAt(), verification.getReport().getId(),
                verification.getId());
        return response;
    }

    @PostMapping("/api/segnalazioni/{id}/verifica")
    public ResponseEntity<NewVerificationResponse> createVerification(@PathVariable(name = "id") Long reportId,
            @RequestBody NewVerificationRequest request) {
        Verification verification = this.verificationService.create(reportId, request.confirmedInsectId(), reportId,
                request.notes(), request.outcome());
        return ResponseEntity.status(HttpStatus.CREATED).body(convert(verification));
    }
}
