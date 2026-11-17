package it.monitoraggioinsetti.monitoraggio_insetti.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import it.monitoraggioinsetti.monitoraggio_insetti.model.Insect;
import it.monitoraggioinsetti.monitoraggio_insetti.service.InsectService;

@RestController
public class InsectController {
    private final InsectService insectService;

    public InsectController(InsectService insectService) {
        this.insectService = insectService;
    }

    @GetMapping("/api/insetti")
    public ResponseEntity<List<Insect>> showAll() {
        return ResponseEntity.ok(this.insectService.findAll());
    }
}
