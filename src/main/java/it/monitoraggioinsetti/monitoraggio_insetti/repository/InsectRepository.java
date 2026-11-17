package it.monitoraggioinsetti.monitoraggio_insetti.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.monitoraggioinsetti.monitoraggio_insetti.model.Insect;

public interface InsectRepository extends JpaRepository<Insect, Long> {

}
