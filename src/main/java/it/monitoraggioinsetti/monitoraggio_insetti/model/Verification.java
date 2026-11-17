package it.monitoraggioinsetti.monitoraggio_insetti.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "verifiche")
public class Verification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "esito", nullable = false, length = 30)
    private String outcome;

    @Column(name = "note", nullable = true, columnDefinition = "TEXT")
    private String notes;

    @Column(name = "data_verifica", nullable = false)
    private LocalDateTime verifiedAt;

    @ManyToOne(optional = false)
    @JoinColumn(name = "segnalazione_id", nullable = false)
    private Report report;

    @ManyToOne(optional = false)
    @JoinColumn(name = "operatore_id", nullable = false)
    private User operator;

    public Verification(String outcome, Report report, User operator, String notes) {
        if (outcome == null || outcome.isBlank())
            throw new IllegalArgumentException("Esito non valido");
        if (report == null)
            throw new IllegalArgumentException("Segnalazione non valida");
        if (operator == null)
            throw new IllegalArgumentException("Operatore non valido");

        if (outcome.length() > 30)
            throw new IllegalArgumentException("Lunghezza esito non valida");

        this.notes = notes;
        this.verifiedAt = LocalDateTime.now();
        this.report = report;
        this.operator = operator;
        this.outcome = outcome;
    }

    protected Verification() {
    }

    public Long getId() {
        return this.id;
    }

    public String getOutcome() {
        return this.outcome;
    }

    public String getNotes() {
        return this.notes;
    }

    public LocalDateTime getVerifiedAt() {
        return this.verifiedAt;
    }

    public Report getReport() {
        return this.report;
    }

    public User getOperator() {
        return this.operator;
    }
}
