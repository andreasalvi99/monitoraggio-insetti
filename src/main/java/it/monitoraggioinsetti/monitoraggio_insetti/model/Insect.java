package it.monitoraggioinsetti.monitoraggio_insetti.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "insetti")
public class Insect {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nome_comune", length = 100, nullable = false)
    private String commonName;

    @Column(name = "nome_scientifico", length = 150, nullable = false, unique = true)
    private String scientificName;

    @Column(name = "descrizione", nullable = true, columnDefinition = "TEXT")
    private String description;

    @Column(name = "immagine_url", nullable = true, length = 500)
    private String imgUrl;

    @Column(name = "attivo", nullable = false)
    private boolean active;

    public Insect(String commonName, String scientificName, String description, String imgUrl) {
        if (commonName == null || commonName.isBlank())
            throw new IllegalArgumentException("Nome comune non valido");
        if (scientificName == null || scientificName.isBlank())
            throw new IllegalArgumentException("Nome scientifico non valido");

        if (commonName.length() > 100)
            throw new IllegalArgumentException("Lunghezza nome comune non valida");
        if (scientificName.length() > 150)
            throw new IllegalArgumentException("Lunghezza nome scientifico non valida");
        if (imgUrl != null && imgUrl.length() > 500)
            throw new IllegalArgumentException("Lunghezza path immagine non valida");

        this.commonName = commonName;
        this.scientificName = scientificName;
        this.description = description;
        this.imgUrl = imgUrl;
        this.active = true;

    }

    protected Insect() {
    }

    public Long getId() {
        return this.id;
    }

    public String getCommonName() {
        return this.commonName;
    }

    public String getScientificName() {
        return this.scientificName;
    }

    public String getDescription() {
        return this.description;
    }

    public String getImgUrl() {
        return this.imgUrl;
    }

    public boolean isActive() {
        return this.active;
    }
}
