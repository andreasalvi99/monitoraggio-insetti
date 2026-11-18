package it.monitoraggioinsetti.monitoraggio_insetti.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import it.monitoraggioinsetti.monitoraggio_insetti.model.Verification;

public interface VerificationRepository extends JpaRepository<Verification, Long> {
    List<Verification> findByReportId(Long reportId);
}
