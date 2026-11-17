package it.monitoraggioinsetti.monitoraggio_insetti.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import it.monitoraggioinsetti.monitoraggio_insetti.dto.request.NewProvinceRequest;
import it.monitoraggioinsetti.monitoraggio_insetti.model.Province;
import it.monitoraggioinsetti.monitoraggio_insetti.service.ProvinceService;

@RestController
public class ProvinceController {
    private final ProvinceService provinceService;

    public ProvinceController(ProvinceService provinceService) {
        this.provinceService = provinceService;
    }

    @GetMapping("/api/province")
    public ResponseEntity<List<Province>> showAll() {
        return ResponseEntity.status(HttpStatus.OK).body(this.provinceService.findAll());
    }

    @PostMapping("/api/province")
    public ResponseEntity<Province> create(@RequestBody NewProvinceRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.provinceService.create(request.name(), request.code()));
    }
}
