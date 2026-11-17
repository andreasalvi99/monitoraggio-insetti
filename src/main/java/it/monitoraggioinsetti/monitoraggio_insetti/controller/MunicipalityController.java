package it.monitoraggioinsetti.monitoraggio_insetti.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import it.monitoraggioinsetti.monitoraggio_insetti.dto.NewMunicipalityRequest;
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

    @PostMapping("/api/comuni")
    public ResponseEntity<Municipality> create(@RequestBody NewMunicipalityRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.municipalityService.create(request.istatCode(), request.name(), request.provinceId()));
    }
}
