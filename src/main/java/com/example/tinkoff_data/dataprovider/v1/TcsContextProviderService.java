package com.example.tinkoff_data.dataprovider.v1;

import com.example.tinkoff_data.dataprovider.v1.TcsContextProvider;
import com.example.tinkoff_data.datastorage.entity.Instrument;
import com.example.tinkoff_data.datastorage.entity.Timeframe;
import com.example.tinkoff_data.model.TimeInterval;
import com.example.tinkoff_data.utils.DateTimeTools;
import com.example.tinkoff_data.utils.TcsTools;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.tinkoff.invest.openapi.model.rest.Candle;
import ru.tinkoff.invest.openapi.model.rest.Candles;
import ru.tinkoff.invest.openapi.model.rest.MarketInstrument;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TcsContextProviderService {

    private final static int MAX_RETRIES = 5;
    private final static int PAUSE_LENGTH = 1000;

    private final TcsContextProvider tcsContextProvider;

    public List<MarketInstrument> getBonds() throws Exception {
        return tcsContextProvider.getBonds().getInstruments();
    }

    public List<MarketInstrument> getEtfs() throws Exception {
        return tcsContextProvider.getEtfs().getInstruments();
    }

    public List<MarketInstrument> getCurrencies() throws Exception {
        return tcsContextProvider.getCurrencies().getInstruments();
    }

    public List<MarketInstrument> getStocks() throws Exception {
        return tcsContextProvider.getStocks().getInstruments();
    }

    public List<Candle> getCandles(
            Instrument instrument, Timeframe timeframe, ZonedDateTime begPeriod, ZonedDateTime endPeriod) {
        int retryCount;
        Optional<Candles> candlesOptional = Optional.empty();
        ArrayList<Candle> candles = new ArrayList<>();
        log.info(String.format("Data request : %s [%s] %s - %s",
                instrument.getTicker(),
                timeframe,
                DateTimeTools.getTimeFormatted(begPeriod),
                DateTimeTools.getTimeFormatted(endPeriod)));
        ArrayList<TimeInterval> periods = (ArrayList<TimeInterval>) TcsTools.splitPeriod(timeframe, begPeriod, endPeriod);
        for (int i = 0; i < periods.size(); i++) {
            log.debug(String.format("Part [%d/%d] %s - %s",
                    i + 1,
                    periods.size(),
                    DateTimeTools.getTimeFormatted(periods.get(i).getBegInterval()),
                    DateTimeTools.getTimeFormatted(periods.get(i).getEndInterval())));
            boolean done = false;
            TimeInterval timeInterval = null;
            retryCount = 0;
            List<Candle> partCandles = new ArrayList<>();
            while (!done) {
                try {
                    timeInterval = periods.get(i);
                    candlesOptional = tcsContextProvider.getCandles(
                            instrument,
                            timeframe,
                            timeInterval.getBegInterval(),
                            timeInterval.getEndInterval());
                } catch (Throwable e) {
                    log.error(String.format("Data request error: %s [%s] %s - %s: %s",
                            instrument.getTicker(),
                            timeframe,
                            timeInterval != null ? DateTimeTools.getTimeFormatted(timeInterval.getBegInterval()) : "?",
                            timeInterval != null ? DateTimeTools.getTimeFormatted(timeInterval.getEndInterval()) : "?",
                            e.getMessage()));
                    retryCount++;
                    if (retryCount >= MAX_RETRIES) {
                        throw new RuntimeException(String.format(
                                "Data request attempts number has exceeded limit of: %d", MAX_RETRIES));
                    }
                    int pause = PAUSE_LENGTH * retryCount;
                    log.debug(String.format(
                            "Data request attempt [%d/%d], pause: %d ms",
                            retryCount, MAX_RETRIES, pause));
                    try {
                        Thread.sleep(pause);
                        continue;
                    } catch (InterruptedException ie) {
                        log.error("Thread interruption error!");
                    }
                }
                partCandles = candlesOptional
                        .orElseThrow(() -> new RuntimeException(
                                String.format("Data not found: %s [%s] (%s-%s)",
                                        instrument.getTicker(),
                                        timeframe,
                                        DateTimeTools.getTimeFormatted(begPeriod),
                                        DateTimeTools.getTimeFormatted(endPeriod))))
                        .getCandles();
                done = true;
            }
            log.debug(String.format("Part [%d/%d] records received: %d", i + 1, periods.size(), partCandles.size()));
            candles.addAll(partCandles);
        }
        log.info(String.format("Records received: %d", candles.size()));
        return candles;

    }
}
