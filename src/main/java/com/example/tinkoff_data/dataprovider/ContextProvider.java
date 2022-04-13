package com.example.tinkoff_data.dataprovider;

import com.example.tinkoff_data.config.ApiConnector;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.tinkoff.invest.openapi.OpenApi;
import ru.tinkoff.invest.openapi.model.rest.MarketInstrumentList;

@Slf4j
@Service
public class ContextProvider {

    private final ApiConnector apiConnector;

    public ContextProvider(ApiConnector apiConnector) {
        this.apiConnector = apiConnector;
    }

    public MarketInstrumentList getStocks() {
        return getOpenApi().getMarketContext().getMarketStocks().join();
    }

    public MarketInstrumentList getBonds() {
        return getOpenApi().getMarketContext().getMarketBonds().join();
    }

    public MarketInstrumentList getEtfs() {
        return getOpenApi().getMarketContext().getMarketEtfs().join();
    }

    public MarketInstrumentList getCurrencies() {
        return getOpenApi().getMarketContext().getMarketCurrencies().join();
    }

    private OpenApi getOpenApi () {
        return apiConnector.getOpenApi();
    }

}
