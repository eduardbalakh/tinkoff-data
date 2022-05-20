package com.example.tinkoff_data.datastorage.service;

import com.example.tinkoff_data.datastorage.entity.v1.Candlestick;
import com.example.tinkoff_data.datastorage.entity.v1.Instrument;
import com.example.tinkoff_data.datastorage.entity.v1.Timeframe;

import java.time.ZonedDateTime;
import java.util.List;

public interface CandlestickService {

    Candlestick getById(Long id);

    List<Candlestick> getCandlesticks(Instrument instrument, Timeframe timeframe);

    List<Candlestick> getCandlesticks(Instrument instrument, Timeframe timeframe, ZonedDateTime begPeriod);

    List<Candlestick> getCandlesticks(
            Instrument instrument, Timeframe timeframe, ZonedDateTime begPeriod, ZonedDateTime endPeriod);

    void saveAllIfNotExists(Candlestick candlestick);

}
