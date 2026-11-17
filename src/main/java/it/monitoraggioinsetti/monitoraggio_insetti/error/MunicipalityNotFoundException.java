package it.monitoraggioinsetti.monitoraggio_insetti.error;

public class MunicipalityNotFoundException extends RuntimeException {
    public MunicipalityNotFoundException(Long municipalityId) {
        super("Comune con ID " + municipalityId + " non trovato");
    }
}
