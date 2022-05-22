package com.example.stocksservice.tinkoff_data.report.reporttypes;

import com.example.stocksservice.tinkoff_data.report.CommonReport;
import ru.tinkoff.invest.openapi.model.rest.MarketInstrument;

import java.util.List;

public class AllCurrenciesReport extends CommonReport {

    public AllCurrenciesReport(List<MarketInstrument> instruments) {
        super();
        this.setFileName("currencies.csv")
                .setReportObjects(instruments)
                .setReportName("Валюты")
                .setFields(new String[]{"ticker", "figi", "name", "currency", "lot", "minPriceIncrement"})
                .setHeaders(new String[]{"тикер", "figi", "наименование", "валюта", "лот", "шаг цены"});
    }

}
