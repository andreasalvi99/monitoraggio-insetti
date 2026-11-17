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
@Table(name = "foto")
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "percorso_file", length = 500, nullable = false)
    private String filePath;

    @Column(name = "data_caricamento", nullable = false)
    private LocalDateTime uploadDate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "segnalazione_id", nullable = false)
    private Report report;

    public Photo(String filePath, Report report) {
        if (filePath == null || filePath.isBlank())
            throw new IllegalArgumentException("Percorso immagine non valido");
        if (report == null)
            throw new IllegalArgumentException("Segnalazione non valida");
        if (filePath.length() > 500)
            throw new IllegalArgumentException("Lunghezza percorso immagine non valida");

        this.uploadDate = LocalDateTime.now();
        this.filePath = filePath;
        this.report = report;
    }

    protected Photo() {
    }

    public Long getId() {
        return this.id;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public LocalDateTime getUploadDate() {
        return this.uploadDate;
    }

    public Report getReport() {
        return this.report;
    }
}
