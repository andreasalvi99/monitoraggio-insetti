package it.monitoraggioinsetti.monitoraggio_insetti.dto.response;

import java.time.LocalDateTime;

public record ReportSummaryResponse(Long id, String status, LocalDateTime reportDate) {

}
