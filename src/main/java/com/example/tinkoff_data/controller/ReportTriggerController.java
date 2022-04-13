package com.example.tinkoff_data.controller;

import com.example.tinkoff_data.report.ReportService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.invest.openapi.model.rest.InstrumentType;

@RestController
@RequestMapping("/report")
public class ReportTriggerController {

    private final ReportService reportService;

    public ReportTriggerController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/trigger-all")
    public void triggerFullImport() {
        reportService.generateAllReports();
    }

    @GetMapping("/trigger/{instrumentType}")
    public void triggerSpecificImport(@PathVariable InstrumentType instrumentType) {
        reportService.generateReport(instrumentType);
    }
}
