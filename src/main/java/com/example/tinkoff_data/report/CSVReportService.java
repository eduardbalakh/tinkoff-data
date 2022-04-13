package com.example.tinkoff_data.report;

import com.example.tinkoff_data.dataprovider.ContextProvider;
import com.example.tinkoff_data.report.reporttypes.AllBondsReport;
import com.example.tinkoff_data.report.reporttypes.AllCurrenciesReport;
import com.example.tinkoff_data.report.reporttypes.AllEtfsReport;
import com.example.tinkoff_data.report.reporttypes.AllStocksReport;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.tinkoff.invest.openapi.model.rest.InstrumentType;
import ru.tinkoff.invest.openapi.model.rest.MarketInstrument;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CSVReportService implements ReportService {

    private final ContextProvider contextProvider;

    @Override
    public void generateAllReports() {
        List<CommonReport> reports = List.of(
                getReportType(InstrumentType.BOND),
                getReportType(InstrumentType.CURRENCY),
                getReportType(InstrumentType.STOCK),
                getReportType(InstrumentType.ETF));
        reports.forEach(CommonReport<MarketInstrument>::doExport);
    }

    @Override
    public void generateReport(InstrumentType instrumentType) {
        CommonReport report = getReportType(instrumentType);
        report.doExport();
    }

    private CommonReport getReportType(InstrumentType instrumentType) {
        switch (instrumentType){
            case BOND:
                return new AllBondsReport(contextProvider.getBonds().getInstruments());
            case CURRENCY:
                return new AllCurrenciesReport(contextProvider.getCurrencies().getInstruments());
            case STOCK:
                return new AllStocksReport(contextProvider.getStocks().getInstruments());
            case ETF:
                return new AllEtfsReport(contextProvider.getEtfs().getInstruments());
            default:
                log.error("Unsupported Instrument Type enum value");
                throw new IllegalArgumentException("Unsupported Instrument Type enum value");
        }
    }
}
