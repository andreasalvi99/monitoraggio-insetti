package it.monitoraggioinsetti.monitoraggio_insetti.error;

public class ProvinceNotFoundException extends RuntimeException {
    public ProvinceNotFoundException(Long provinceId) {
        super("Provincia con ID " + provinceId + " non trovata");
    }
}
