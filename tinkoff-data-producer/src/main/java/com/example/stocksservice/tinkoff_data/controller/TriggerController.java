package com.example.stocksservice.tinkoff_data.controller;

import com.example.stocksservice.tinkoff_data.datastorage.service.MarketInstrumentService;
import com.example.stocksservice.tinkoff_data.report.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.invest.openapi.model.rest.InstrumentType;

@RestController
@RequestMapping("/trigger")
@RequiredArgsConstructor
public class TriggerController {

    private final ReportService reportService;

    private final MarketInstrumentService marketInstrumentService;

    @GetMapping("/report")
    public void triggerAllReports() throws Exception {
        reportService.generateAllReports();
    }

    @GetMapping("/report/{instrumentType}")
    public void triggerSpecificImport(@PathVariable InstrumentType instrumentType) throws Exception {
        reportService.generateReport(instrumentType);
    }

    @GetMapping("/import/instruments")
    public void triggerDatabaseInstrumentsUpdate() throws Exception {
        marketInstrumentService.updateInstruments();
    }
}
