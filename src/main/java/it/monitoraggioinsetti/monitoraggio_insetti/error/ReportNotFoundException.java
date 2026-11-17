package it.monitoraggioinsetti.monitoraggio_insetti.error;

public class ReportNotFoundException extends RuntimeException {
    public ReportNotFoundException(Long reportId) {
        super("Segnalazione con ID " + reportId + " non trovata");
    }
}
