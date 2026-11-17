package it.monitoraggioinsetti.monitoraggio_insetti.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.monitoraggioinsetti.monitoraggio_insetti.model.Report;

public interface ReportRepository extends JpaRepository<Report, Long> {

}
