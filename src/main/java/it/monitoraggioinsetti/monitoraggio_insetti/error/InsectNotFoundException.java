package it.monitoraggioinsetti.monitoraggio_insetti.error;

public class InsectNotFoundException extends RuntimeException {
    public InsectNotFoundException(Long insectId) {
        super("Insetto con ID " + insectId + " non trovato");
    }
}
