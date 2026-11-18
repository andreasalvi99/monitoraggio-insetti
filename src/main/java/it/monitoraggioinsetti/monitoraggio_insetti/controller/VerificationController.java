package it.monitoraggioinsetti.monitoraggio_insetti.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import it.monitoraggioinsetti.monitoraggio_insetti.dto.request.NewVerificationRequest;
import it.monitoraggioinsetti.monitoraggio_insetti.dto.response.NewVerificationResponse;
import it.monitoraggioinsetti.monitoraggio_insetti.model.Verification;
import it.monitoraggioinsetti.monitoraggio_insetti.service.UserService;
import it.monitoraggioinsetti.monitoraggio_insetti.service.VerificationService;

@RestController
public class VerificationController {
    private final VerificationService verificationService;
    private final UserService userService;

    public VerificationController(VerificationService verificationService, UserService userService) {
        this.verificationService = verificationService;
        this.userService = userService;
    }

    private NewVerificationResponse convert(Verification verification) {
        NewVerificationResponse response = new NewVerificationResponse(verification.getOutcome(),
                verification.getNotes(), verification.getVerifiedAt(), verification.getReport().getId(),
                verification.getId());
        return response;
    }

    @GetMapping("/api/segnalazioni/{id}/storico-verifiche")
    public ResponseEntity<List<Verification>> showHistory(@PathVariable(name = "id") Long reportId) {
        return ResponseEntity.ok(this.verificationService.showHistory(reportId));
    }

    @PostMapping("/api/segnalazioni/{id}/verifica")
    public ResponseEntity<NewVerificationResponse> createVerification(@PathVariable(name = "id") Long reportId,
            @RequestBody NewVerificationRequest request, Principal principal) {
        Long operatorId = this.userService.findIdByEmail(principal.getName());
        Verification verification = this.verificationService.create(reportId, request.confirmedInsectId(), operatorId,
                request.notes(), request.outcome());
        return ResponseEntity.status(HttpStatus.CREATED).body(convert(verification));
    }
}
