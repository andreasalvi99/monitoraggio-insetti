package it.monitoraggioinsetti.monitoraggio_insetti.dto.request;

import java.math.BigDecimal;

public record NewReportRequest(BigDecimal latitude, BigDecimal longitude, Long reportedInsectId, Long municipalityId,
                String reporterEmail, String description) {

}
