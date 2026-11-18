package it.monitoraggioinsetti.monitoraggio_insetti.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.ui.Model;
import it.monitoraggioinsetti.monitoraggio_insetti.service.ReportService;

@Controller
public class DashboardController {
    private final ReportService reportService;

    public DashboardController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/segnalazioni")
    public String showReports(Model model) {
        model.addAttribute("reports", this.reportService.findAll());
        return "reports";
    }
}
