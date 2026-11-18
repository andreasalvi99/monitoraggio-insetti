package it.monitoraggioinsetti.monitoraggio_insetti.error;

public class CaseNotFoundException extends RuntimeException {
    public CaseNotFoundException(Long caseId) {
        super("La pratica con ID " + caseId + " non è stata trovata");
    }
}
