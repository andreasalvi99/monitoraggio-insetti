package it.monitoraggioinsetti.monitoraggio_insetti.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import it.monitoraggioinsetti.monitoraggio_insetti.error.OperationNotAllowedException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "pratiche")
public class Case {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "stato", nullable = false, length = 30)
    private CaseStatus status;

    @Column(name = "data_apertura", nullable = false)
    private LocalDateTime openedAt;

    @Column(name = "data_chiusura")
    private LocalDateTime closedAt;

    @Column(name = "descrizione", nullable = true, columnDefinition = "TEXT")
    private String description;

    @Column(name = "latitudine", nullable = false, precision = 10, scale = 7)
    private BigDecimal latitude;

    @Column(name = "longitudine", nullable = false, precision = 10, scale = 7)
    private BigDecimal longitude;

    @ManyToOne
    @JoinColumn(name = "operatore_id")
    private User operator;

    @ManyToOne(optional = false)
    @JoinColumn(name = "aperta_da_id", nullable = false)
    private User openedBy;

    public Case(String description, BigDecimal latitude, BigDecimal longitude, User operator, User openedBy) {

        if (description != null && description.isBlank())
            throw new IllegalArgumentException("Descrizione non valida");
        if (latitude == null)
            throw new IllegalArgumentException("La latitudine non è valida");
        if (longitude == null)
            throw new IllegalArgumentException("La longitudine non è valida");
        if (openedBy == null)
            throw new IllegalArgumentException("Il titolare della pratica non è valido");

        if (latitude.compareTo(BigDecimal.valueOf(90)) > 0)
            throw new IllegalArgumentException("La latitudine non può essere maggiore di 90");
        if (latitude.compareTo(BigDecimal.valueOf(-90)) < 0)
            throw new IllegalArgumentException("La latitudine non può essere minore di -90");
        if (longitude.compareTo(BigDecimal.valueOf(180)) > 0)
            throw new IllegalArgumentException("La longitudine non può essere maggiore di 180");
        if (longitude.compareTo(BigDecimal.valueOf(-180)) < 0)
            throw new IllegalArgumentException("La longitudine non può essere minore di -180");
        if (latitude.stripTrailingZeros().scale() > 7)
            throw new IllegalArgumentException("Limite massimo di decimali superato");
        if (longitude.stripTrailingZeros().scale() > 7)
            throw new IllegalArgumentException("Limite massimo di decimali superato");

        this.status = CaseStatus.APERTA;
        this.description = description;
        this.openedAt = LocalDateTime.now();
        this.latitude = latitude;
        this.longitude = longitude;
        this.operator = operator;
        this.openedBy = openedBy;
    }

    protected Case() {
    }

    public Long getId() {
        return this.id;
    }

    public CaseStatus getStatus() {
        return this.status;
    }

    public String getDescription() {
        return this.description;
    }

    public LocalDateTime getOpenedAt() {
        return this.openedAt;
    }

    public LocalDateTime getClosedAt() {
        return this.closedAt;
    }

    public BigDecimal getLatitude() {
        return this.latitude;
    }

    public BigDecimal getLongitude() {
        return this.longitude;
    }

    public User getOperator() {
        return this.operator;
    }

    public User getOpenedBy() {
        return this.openedBy;
    }

    public void startWork() {
        if (this.status == CaseStatus.IN_LAVORAZIONE)
            throw new OperationNotAllowedException("La pratica è già in lavorazione");
        this.status = CaseStatus.IN_LAVORAZIONE;

        this.closedAt = null;
    }

    public void putOnHold() {
        if (this.status == CaseStatus.IN_ATTESA)
            throw new OperationNotAllowedException("La pratica è già in attesa");
        this.status = CaseStatus.IN_ATTESA;

        this.closedAt = null;
    }

    public void complete() {
        if (this.status == CaseStatus.EVASA)
            throw new OperationNotAllowedException("La pratica è già stata evasa");
        this.status = CaseStatus.EVASA;

        this.closedAt = LocalDateTime.now();
    }

    public void fail() {
        if (this.status == CaseStatus.FALLITA)
            throw new OperationNotAllowedException("La pratica è già fallita");
        this.status = CaseStatus.FALLITA;

        this.closedAt = LocalDateTime.now();
    }
}
