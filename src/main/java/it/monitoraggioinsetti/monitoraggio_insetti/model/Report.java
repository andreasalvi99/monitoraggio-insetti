package it.monitoraggioinsetti.monitoraggio_insetti.model;

import java.math.BigDecimal;
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
@Table(name = "segnalazioni")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "codice_tracciamento_hash", nullable = false, unique = true, length = 64, columnDefinition = "CHAR(64)")
    private String trackingCodeHash;

    @Column(name = "email_segnalante", nullable = true, length = 255)
    private String reporterEmail;

    @Column(name = "descrizione", nullable = true, columnDefinition = "TEXT")
    private String description;

    @Column(name = "latitudine", nullable = false, precision = 10, scale = 7)
    private BigDecimal latitude;

    @Column(name = "longitudine", nullable = false, precision = 10, scale = 7)
    private BigDecimal longitude;

    @Column(name = "data_segnalazione", nullable = false)
    private LocalDateTime reportDate;

    @Column(name = "stato", nullable = false, length = 30)
    private String status;

    @ManyToOne(optional = false)
    @JoinColumn(name = "insetto_segnalato_id", nullable = false)
    private Insect reportedInsect;

    @ManyToOne
    @JoinColumn(name = "insetto_accertato_id")
    private Insect confirmedInsect;

    @ManyToOne(optional = false)
    @JoinColumn(name = "comune_id", nullable = false)
    private Municipality municipality;

    @ManyToOne
    @JoinColumn(name = "pratica_id")
    private Case associatedCase;

    public Report(String trackingCodeHash, BigDecimal latitude, BigDecimal longitude, Insect reportedInsect,
            Municipality municipality, String reporterEmail, String description) {

        if (trackingCodeHash == null || !trackingCodeHash.matches("[0-9a-fA-F]{64}"))
            throw new IllegalArgumentException("Codice tracciamento hash non valido");
        if (latitude == null)
            throw new IllegalArgumentException("Latitudine non valida");
        if (longitude == null)
            throw new IllegalArgumentException("Longitudine non valida");
        if (reportedInsect == null)
            throw new IllegalArgumentException("Insetto segnalato non valido");
        if (municipality == null)
            throw new IllegalArgumentException("Comune non valido");

        if (reporterEmail != null && reporterEmail.length() > 255)
            throw new IllegalArgumentException("Lunghezza email non valida");
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

        this.trackingCodeHash = trackingCodeHash;
        this.reporterEmail = reporterEmail;
        this.description = description;
        this.reportedInsect = reportedInsect;
        this.latitude = latitude;
        this.longitude = longitude;
        this.municipality = municipality;
        this.status = "IN_ATTESA";
        this.reportDate = LocalDateTime.now();
    }

    protected Report() {

    }

    public Long getId() {
        return this.id;
    }

    public String getReporterEmail() {
        return this.reporterEmail;
    }

    public String getDescription() {
        return this.description;
    }

    public Insect getReportedInsect() {
        return this.reportedInsect;
    }

    public Insect getConfirmedInsect() {
        return this.confirmedInsect;
    }

    public BigDecimal getLatitude() {
        return this.latitude;
    }

    public BigDecimal getLongitude() {
        return this.longitude;
    }

    public LocalDateTime getReportDate() {
        return this.reportDate;
    }

    public String getStatus() {
        return this.status;
    }

    public Municipality getMunicipality() {
        return this.municipality;
    }

    public Case getAssociatedCase() {
        return this.associatedCase;
    }

    public void confirm(Insect confirmInsect) {
        if (confirmInsect == null)
            throw new IllegalArgumentException("Insetto non valido");

        this.confirmedInsect = confirmInsect;
        this.status = "ACCERTATA";
    }
}
