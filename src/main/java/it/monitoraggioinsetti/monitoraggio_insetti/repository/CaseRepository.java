package it.monitoraggioinsetti.monitoraggio_insetti.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import it.monitoraggioinsetti.monitoraggio_insetti.model.Case;
import it.monitoraggioinsetti.monitoraggio_insetti.model.Report;
import jakarta.persistence.LockModeType;

public interface CaseRepository extends JpaRepository<Case, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select r from Report r where r.id = :id")
    Optional<Report> findByForUpdate(@Param("id") Long id);
}
