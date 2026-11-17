package it.monitoraggioinsetti.monitoraggio_insetti.service;

import java.util.List;

import org.springframework.stereotype.Service;

import it.monitoraggioinsetti.monitoraggio_insetti.error.ProvinceNotFoundException;
import it.monitoraggioinsetti.monitoraggio_insetti.model.Municipality;
import it.monitoraggioinsetti.monitoraggio_insetti.model.Province;
import it.monitoraggioinsetti.monitoraggio_insetti.repository.MunicipalityRepository;
import it.monitoraggioinsetti.monitoraggio_insetti.repository.ProvinceRepository;

@Service
public class MunicipalityService {
    private final MunicipalityRepository municipalityRepository;
    private final ProvinceRepository provinceRepository;

    public MunicipalityService(MunicipalityRepository municipalityRepository, ProvinceRepository provinceRepository) {
        this.municipalityRepository = municipalityRepository;
        this.provinceRepository = provinceRepository;
    }

    private Province findProvince(Long provinceId) {
        if (provinceId == null)
            throw new IllegalArgumentException("L'ID della provincia è obbligatorio");
        return this.provinceRepository.findById(provinceId)
                .orElseThrow(() -> new ProvinceNotFoundException(provinceId));
    }

    public List<Municipality> findAll() {
        return this.municipalityRepository.findAll();
    }

    public Municipality create(String istatCode, String name, Long provinceId) {
        Province toFind = findProvince(provinceId);
        Municipality newMunicipality = new Municipality(istatCode, name, toFind);
        return this.municipalityRepository.save(newMunicipality);
    }
}
