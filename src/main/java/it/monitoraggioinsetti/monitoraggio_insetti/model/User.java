package it.monitoraggioinsetti.monitoraggio_insetti.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "utenti")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nome", nullable = false, length = 100)
    private String name;

    @Column(name = "email", nullable = false, unique = true, length = 255)
    private String email;

    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;

    @Column(name = "ruolo", nullable = false, length = 30)
    private String role;

    @Column(name = "attivo", nullable = false)
    private boolean active;

    public User(String name, String email, String passwordHash, String role) {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Nome utente non valido");
        if (email == null || email.isBlank())
            throw new IllegalArgumentException("Email non valida");
        if (passwordHash == null || passwordHash.isBlank())
            throw new IllegalArgumentException("Hash della password non valido");
        if (role == null || role.isBlank())
            throw new IllegalArgumentException("Ruolo non valido");

        if (name.length() > 100)
            throw new IllegalArgumentException("Lunghezza nome non valida");
        if (email.length() > 255)
            throw new IllegalArgumentException("Lunghezza email non valida");
        if (passwordHash.length() > 255)
            throw new IllegalArgumentException("Lunghezza hash della password non valida");
        if (role.length() > 30)
            throw new IllegalArgumentException("Lunghezza ruolo non valida");

        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
        this.active = true;
    }

    protected User() {
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    // public String getPasswordHash() {
    // return this.passwordHash;
    // }

    public String getRole() {
        return this.role;
    }

    public boolean isActive() {
        return this.active;
    }
}
