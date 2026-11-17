package it.monitoraggioinsetti.monitoraggio_insetti.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.monitoraggioinsetti.monitoraggio_insetti.model.Verification;

public interface VerificationRepository extends JpaRepository<Verification, Long> {

}
