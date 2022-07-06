package com.example.stocksservice.tinkoff_data.datastorage.service;

import com.example.stocksservice.tinkoff_data.datastorage.entity.v1.Instrument;
import com.example.stocksservice.tinkoff_data.datastorage.entity.v1.InstrumentType;
import com.example.stocksservice.tinkoff_data.datastorage.entity.v1.Timeframe;

import java.time.ZonedDateTime;

public interface MarketInstrumentService {

    /**
     * обновляет информацию по инструментам
     */
    void updateInstruments() throws Exception;

    /**
     * обновляет информацию по инструменту
     *
     * @param instrumentTypeCode - код типа инструмента
     */
    void updateInstruments(String instrumentTypeCode) throws Exception;

    /**
     * обновляет информацию по инструменту
     *
     * @param instrumentType - тип инструмента
     */
    void updateInstruments(InstrumentType instrumentType) throws Exception;
}
