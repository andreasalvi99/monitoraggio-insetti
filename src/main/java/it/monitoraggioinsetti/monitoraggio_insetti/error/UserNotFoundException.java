package it.monitoraggioinsetti.monitoraggio_insetti.error;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long userId) {
        super("Utente con ID " + userId + " non trovato");
    }
}
