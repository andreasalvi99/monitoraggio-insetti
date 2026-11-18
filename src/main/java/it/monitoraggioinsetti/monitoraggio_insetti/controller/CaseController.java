package it.monitoraggioinsetti.monitoraggio_insetti.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import it.monitoraggioinsetti.monitoraggio_insetti.dto.request.ChangeStatusRequest;
import it.monitoraggioinsetti.monitoraggio_insetti.dto.request.NewCaseRequest;
import it.monitoraggioinsetti.monitoraggio_insetti.model.Case;
import it.monitoraggioinsetti.monitoraggio_insetti.service.CaseService;
import it.monitoraggioinsetti.monitoraggio_insetti.service.UserService;

@RestController
public class CaseController {
    private final CaseService caseService;
    private final UserService userService;

    public CaseController(CaseService caseService, UserService userService) {
        this.caseService = caseService;
        this.userService = userService;
    }

    @PostMapping("/api/pratiche")
    public ResponseEntity<List<Case>> createCase(@RequestBody NewCaseRequest request, Principal principal) {
        Long operatorId = this.userService.findIdByEmail(principal.getName());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.caseService.create(request.reportIds(), request.description(), operatorId));
    }

    @PatchMapping("/api/pratiche/{id}")
    public ResponseEntity<Case> changeStatus(@PathVariable(name = "id") Long caseId,
            @RequestBody ChangeStatusRequest request) {
        return ResponseEntity.ok(this.caseService.changeStatus(caseId, request.status()));
    }
}
