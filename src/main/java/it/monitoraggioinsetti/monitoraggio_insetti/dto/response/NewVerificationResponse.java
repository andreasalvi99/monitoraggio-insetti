package it.monitoraggioinsetti.monitoraggio_insetti.dto.response;

import java.time.LocalDateTime;

public record NewVerificationResponse(String outcome, String notes, LocalDateTime verifiedAt, Long reportId, Long id) {

}
