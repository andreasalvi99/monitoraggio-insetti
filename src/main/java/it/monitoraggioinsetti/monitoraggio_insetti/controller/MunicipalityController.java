package it.monitoraggioinsetti.monitoraggio_insetti.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import it.monitoraggioinsetti.monitoraggio_insetti.model.Municipality;
import it.monitoraggioinsetti.monitoraggio_insetti.service.MunicipalityService;

@RestController
public class MunicipalityController {
    private final MunicipalityService municipalityService;

    public MunicipalityController(MunicipalityService municipalityService) {
        this.municipalityService = municipalityService;
    }

    @GetMapping("/api/comuni")
    public ResponseEntity<List<Municipality>> showAll() {
        return ResponseEntity.ok(this.municipalityService.findAll());
    }
}
