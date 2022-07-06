package com.example.stocksservice.tinkoff_data.controller;

import com.example.stocksservice.tinkoff_data.dataprovider.v2.TcsApiProvider;
import com.example.stocksservice.tinkoff_data.dataprovider.v2.model.MarketInstrument;
import com.example.stocksservice.tinkoff_data.datastorage.service.CacheInstrumentServiceImpl;
import com.example.stocksservice.tinkoff_data.datastorage.service.MarketInstrumentService;
import com.example.stocksservice.tinkoff_data.report.ReportService;
import com.google.protobuf.AbstractMessage;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.invest.openapi.model.rest.InstrumentType;
import ru.tinkoff.piapi.contract.v1.InstrumentStatus;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/trigger")
@AllArgsConstructor
public class TriggerController {

    private final ReportService reportService;

    private final MarketInstrumentService marketInstrumentService;

    private final TcsApiProvider contextProviderV2_1;

    private CacheInstrumentServiceImpl cacheInstrumentService;

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

    @GetMapping("/import/instruments/bonds")
    public ResponseEntity<List<String>> getBonds() {
        var body = contextProviderV2_1.getApi().getInstrumentsService().getBondsSync(InstrumentStatus.INSTRUMENT_STATUS_ALL).stream().map(
                AbstractMessage::toString
        ).collect(Collectors.toList());
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @GetMapping("/getbyfigi")
    public ResponseEntity<List<MarketInstrument>> getByFigi() {
        var body = cacheInstrumentService.getAll();
        return new ResponseEntity<>(body, HttpStatus.OK);
    }
}
