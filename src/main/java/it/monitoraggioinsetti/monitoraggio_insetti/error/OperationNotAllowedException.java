package it.monitoraggioinsetti.monitoraggio_insetti.error;

public class OperationNotAllowedException extends RuntimeException {
    public OperationNotAllowedException(String message) {
        super(message);
    }
}
