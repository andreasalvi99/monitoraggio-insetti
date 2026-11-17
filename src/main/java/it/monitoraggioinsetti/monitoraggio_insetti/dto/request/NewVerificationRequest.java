package it.monitoraggioinsetti.monitoraggio_insetti.dto.request;

public record NewVerificationRequest(String outcome, Long confirmedInsectId, String notes) {

}
