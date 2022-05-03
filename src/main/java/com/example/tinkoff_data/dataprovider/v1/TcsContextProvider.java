package com.example.tinkoff_data.dataprovider.v1;

import com.example.tinkoff_data.config.ApiConnector;
import com.example.tinkoff_data.datastorage.entity.Instrument;
import com.example.tinkoff_data.datastorage.entity.Timeframe;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.tinkoff.invest.openapi.OpenApi;
import ru.tinkoff.invest.openapi.model.rest.CandleResolution;
import ru.tinkoff.invest.openapi.model.rest.Candles;
import ru.tinkoff.invest.openapi.model.rest.MarketInstrumentList;

import java.time.ZonedDateTime;
import java.util.Optional;

@Slf4j
@Component
@AllArgsConstructor
public class TcsContextProvider {

    private final ApiConnector tcsApiConnector;
    private final ModelMapper modelMapper;

    public MarketInstrumentList getStocks() throws Exception {
        return getOpenApi().getMarketContext().getMarketStocks().join();
    }

    public MarketInstrumentList getBonds() throws Exception {
        return getOpenApi().getMarketContext().getMarketBonds().join();
    }

    public MarketInstrumentList getEtfs() throws Exception {
        return getOpenApi().getMarketContext().getMarketEtfs().join();
    }

    public MarketInstrumentList getCurrencies() throws Exception {
        return getOpenApi().getMarketContext().getMarketCurrencies().join();
    }

    public Optional<Candles> getCandles(
            Instrument instrument, Timeframe timeframe, ZonedDateTime begPeriod, ZonedDateTime endPeriod)
            throws Exception {
        return getOpenApi().getMarketContext().getMarketCandles(
                instrument.getFigi(),
                begPeriod.toOffsetDateTime(),
                endPeriod.toOffsetDateTime(),
                modelMapper.map(timeframe, CandleResolution.class)).join();
    }

    private OpenApi getOpenApi() {
        return tcsApiConnector.getOpenApi();
    }

}