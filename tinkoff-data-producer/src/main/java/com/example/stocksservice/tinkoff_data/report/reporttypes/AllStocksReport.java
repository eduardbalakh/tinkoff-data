package com.example.stocksservice.tinkoff_data.report.reporttypes;

import com.example.stocksservice.tinkoff_data.report.CommonReport;
import ru.tinkoff.invest.openapi.model.rest.MarketInstrument;

import java.util.List;

public class AllStocksReport extends CommonReport {

    public AllStocksReport(List<MarketInstrument> instruments) {
        super();
        this.setFileName("stocks.csv")
                .setReportObjects(instruments)
                .setReportName("Акции")
                .setFields(new String[]{"ticker", "isin", "figi", "name", "currency", "lot", "minPriceIncrement"})
                .setHeaders(new String[]{"тикер", "isin", "figi", "наименование", "валюта", "лот", "шаг цены"});
    }

}
