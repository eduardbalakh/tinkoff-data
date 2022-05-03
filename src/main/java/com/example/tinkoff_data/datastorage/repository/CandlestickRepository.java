package com.example.tinkoff_data.datastorage.repository;

import com.example.tinkoff_data.datastorage.entity.Candlestick;
import com.example.tinkoff_data.datastorage.entity.Instrument;
import com.example.tinkoff_data.datastorage.entity.Timeframe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface CandlestickRepository extends JpaRepository<Candlestick, Long> {

    List<Candlestick> getCandlestickByInstrumentAndTimeframeAndSinceBetweenOrderBySince(
            Instrument instrument, Timeframe timeframe, ZonedDateTime begPeriod, ZonedDateTime endPeriod);

    boolean existsByInstrumentAndTimeframeAndSince(Instrument instrument, Timeframe timeframe, ZonedDateTime since);

}
