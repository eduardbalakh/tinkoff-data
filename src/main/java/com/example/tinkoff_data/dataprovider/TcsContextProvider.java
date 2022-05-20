package com.example.tinkoff_data.dataprovider;

import com.example.tinkoff_data.datastorage.entity.v1.Instrument;
import com.example.tinkoff_data.datastorage.entity.v1.Timeframe;
import ru.tinkoff.invest.openapi.model.rest.Candles;
import ru.tinkoff.invest.openapi.model.rest.MarketInstrumentList;

import java.time.ZonedDateTime;
import java.util.Optional;

public interface TcsContextProvider {

    MarketInstrumentList getStocks();
    MarketInstrumentList getBonds();
    MarketInstrumentList getEtfs();
    MarketInstrumentList getCurrencies();
    Optional<Candles> getCandles(Instrument instrument, Timeframe timeframe, ZonedDateTime begPeriod, ZonedDateTime endPeriod);

}
