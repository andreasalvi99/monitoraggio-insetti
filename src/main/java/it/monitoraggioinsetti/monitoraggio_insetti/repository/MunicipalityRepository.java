package it.monitoraggioinsetti.monitoraggio_insetti.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.monitoraggioinsetti.monitoraggio_insetti.model.Municipality;

public interface MunicipalityRepository extends JpaRepository<Municipality, Long> {

}
