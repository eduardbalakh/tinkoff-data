package com.example.tinkoff_data.datastorage.service;

import com.example.tinkoff_data.datastorage.entity.v1.Candlestick;
import com.example.tinkoff_data.datastorage.entity.v1.Instrument;
import com.example.tinkoff_data.datastorage.entity.v1.Timeframe;
import com.example.tinkoff_data.datastorage.repository.CandlestickRepository;
import com.example.tinkoff_data.utils.DateTimeTools;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.ZonedDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class CandlestickServiceImpl implements CandlestickService {

    private CandlestickRepository candlestickRepository;

    @Override
    public Candlestick getById(Long id) {
        return candlestickRepository.getById(id);
    }

    @Override
    public List<Candlestick> getCandlesticks(Instrument instrument, Timeframe timeframe) {
        return getCandlesticks(instrument, timeframe, null, null);
    }

    @Override
    public List<Candlestick> getCandlesticks(Instrument instrument, Timeframe timeframe, ZonedDateTime begPeriod) {
        return getCandlesticks(instrument, timeframe, begPeriod);
    }

    @Override
    public List<Candlestick> getCandlesticks(
            Instrument instrument, Timeframe timeframe, ZonedDateTime begPeriod, ZonedDateTime endPeriod) {
        if (begPeriod == null)
            begPeriod = DateTimeTools.parseDate("01.01.2020");
        if (endPeriod == null)
            endPeriod = ZonedDateTime.now();
        DateTimeTools.checkInterval(begPeriod, endPeriod);
        return candlestickRepository.getCandlestickByInstrumentAndTimeframeAndSinceBetweenOrderBySince(
                instrument, timeframe, begPeriod, endPeriod);
    }

    @Override
    @Transactional
    public void saveAllIfNotExists(Candlestick candlestick) {
        if (!candlestickRepository.existsByInstrumentAndTimeframeAndSince(
                candlestick.getInstrument(), candlestick.getTimeframe(), candlestick.getSince()))
            candlestickRepository.saveAndFlush(candlestick);
    }
}
