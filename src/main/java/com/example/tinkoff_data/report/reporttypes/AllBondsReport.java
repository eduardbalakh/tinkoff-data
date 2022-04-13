package com.example.tinkoff_data.report.reporttypes;

import com.example.tinkoff_data.report.CommonReport;
import ru.tinkoff.invest.openapi.model.rest.MarketInstrument;

import java.util.List;

public class AllBondsReport extends CommonReport {

    public AllBondsReport(List<MarketInstrument> instruments) {
        super();
        this.setFileName("bonds.csv")
                .setReportObjects(instruments)
                .setReportName("Облигации")
                .setFields(new String[]{"ticker", "isin", "figi", "name", "currency", "lot", "minPriceIncrement"})
                .setHeaders(new String[]{"тикер", "isin", "figi", "наименование", "валюта", "лот", "шаг цены"});
    }

}
