package it.monitoraggioinsetti.monitoraggio_insetti.dto.request;

import java.util.List;

public record NewCaseRequest(List<Long> reportIds, String description) {

}
