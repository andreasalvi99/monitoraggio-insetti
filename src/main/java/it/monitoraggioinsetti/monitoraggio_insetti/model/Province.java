package it.monitoraggioinsetti.monitoraggio_insetti.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "province")
public class Province {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nome", nullable = false, length = 100)
    private String name;

    @Column(name = "sigla", nullable = false, length = 2, unique = true)
    private String code;

    public Province(String name, String code) {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Il nome della provincia non è valido");
        if (code == null || code.isBlank())
            throw new IllegalArgumentException("La sigla della provincia non è valida");

        if (name.length() > 100)
            throw new IllegalArgumentException("Il nome della provincia è troppo lungo");
        if (code.length() != 2)
            throw new IllegalArgumentException("La sigla della provincia ammette solo due caratteri");

        this.name = name;
        this.code = code;
    }

    protected Province() {
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getCode() {
        return this.code;
    }

}
