package it.monitoraggioinsetti.monitoraggio_insetti.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "comuni")
public class Municipality {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "codice_istat", nullable = false, unique = true, length = 6)
    private String istatCode;

    @Column(name = "nome", nullable = false, length = 100)
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "provincia_id", nullable = false)
    private Province province;

    public Municipality(String istatCode, String name, Province province) {
        if (istatCode == null || istatCode.isBlank())
            throw new IllegalArgumentException("Codice ISTAT non valido");
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Nome del municipio non valido");
        if (province == null)
            throw new IllegalArgumentException("Provincia non valida");

        if (name.length() > 100)
            throw new IllegalArgumentException("La lunghezza del nome del comune non è valida");
        if (!istatCode.matches("[0-9]{6}"))
            throw new IllegalArgumentException("Il codice ISTAT ammette solo sei caratteri");

        this.istatCode = istatCode;
        this.name = name;
        this.province = province;
    }

    protected Municipality() {
    }

    public Long getId() {
        return this.id;
    }

    public String getIstatCode() {
        return this.istatCode;
    }

    public String getName() {
        return this.name;
    }

    public Province getProvince() {
        return this.province;
    }

}
