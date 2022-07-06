package com.example.stocksservice.tinkoff_data.report;

import com.example.stocksservice.tinkoff_data.service.InstrumentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.tinkoff.invest.openapi.model.rest.InstrumentType;

@Service
@RequiredArgsConstructor
@Slf4j
public class CSVReportService implements ReportService {

    private InstrumentService instrumentService;

    @Override
    public void generateAllReports() throws Exception {
        /*List<CommonReport> reports = List.of(
                getReportType(InstrumentType.BOND),
                getReportType(InstrumentType.CURRENCY),
                getReportType(InstrumentType.STOCK),
                getReportType(InstrumentType.ETF));
        reports.forEach(CommonReport<MarketInstrument>::doExport);*/
    }

    @Override
    public void generateReport(InstrumentType instrumentType) throws Exception {
        //CommonReport report = getReportType(instrumentType);
        //report.doExport();
    }

    /*private CommonReport getReportType(InstrumentType instrumentType) throws Exception {
        switch (instrumentType){
            case BOND:
                return new AllBondsReport(instrumentService.getInstruments(InstrumentType.BOND).getBonds());
            case CURRENCY:
                return new AllCurrenciesReport(instrumentService.getCurrencies());
            case STOCK:
                return new AllStocksReport(instrumentService.getStocks());
            case ETF:
                return new AllEtfsReport(instrumentService.getEtfs());
            default:
                log.error("Unsupported Instrument Type enum value");
                throw new IllegalArgumentException("Unsupported Instrument Type enum value");
        }
    }*/
}
