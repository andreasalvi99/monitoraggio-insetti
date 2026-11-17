package it.monitoraggioinsetti.monitoraggio_insetti.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import it.monitoraggioinsetti.monitoraggio_insetti.dto.NewInsectRequest;
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

    @PostMapping("/api/insetti")
    public ResponseEntity<Insect> create(@RequestBody NewInsectRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.insectService.create(request.commonName(),
                request.scientificName(), request.description(), request.imgUrl()));
    }
}
