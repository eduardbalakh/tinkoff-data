package com.example.tinkoff_data.datastorage.service;

import com.example.tinkoff_data.datastorage.entity.Instrument;
import com.example.tinkoff_data.datastorage.entity.InstrumentType;
import com.example.tinkoff_data.datastorage.entity.Timeframe;

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

    /**
     * обновляет исторические данные по инструменту
     * период - с начала GlobalContext.BEG_DATE - по настоещее время
     *
     * @param instrument - инструмент
     * @param timeframe  - таймфрейм
     */
    void updateCandlesticks(Instrument instrument, Timeframe timeframe);

    /**
     * обновляет исторические данные по инструменту
     * период - с начала GlobalContext.BEG_DATE - по настоещее время
     *
     * @param instrument  - инструмент
     * @param timeframe   - таймфрейм
     * @param begInterval - начало периода
     * @param endInterval - окончание периода
     */
    void updateCandlesticks(
            Instrument instrument, Timeframe timeframe, ZonedDateTime begInterval, ZonedDateTime endInterval);


}
