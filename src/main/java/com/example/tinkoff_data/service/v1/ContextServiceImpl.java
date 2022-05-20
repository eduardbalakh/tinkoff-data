package com.example.tinkoff_data.service.v1;

import com.example.tinkoff_data.dataprovider.v1.TcsContextProviderService;
import com.example.tinkoff_data.datastorage.entity.v1.Candlestick;
import com.example.tinkoff_data.datastorage.entity.v1.Instrument;
import com.example.tinkoff_data.datastorage.entity.v1.InstrumentType;
import com.example.tinkoff_data.datastorage.entity.v1.Timeframe;
import com.example.tinkoff_data.datastorage.service.InstrumentTypeServiceImpl;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.tinkoff.invest.openapi.model.rest.MarketInstrument;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class ContextServiceImpl {

    private final TcsContextProviderService tcsContextService;
    private final InstrumentTypeServiceImpl instrumentTypeService;
    private final ModelMapper modelMapper;

    public List<Instrument> getInstruments() throws Exception {
        log.debug("Downloading all instruments");
        List<InstrumentType> instrumentTypes;
        List<Instrument> instruments = new ArrayList<>();
        instrumentTypes = instrumentTypeService.getAll();
        for (InstrumentType instrumentType : instrumentTypes) {
            instruments.addAll(getInstruments(instrumentType));
        }
        return instruments;
    }

    public List<Instrument> getInstruments(@NonNull InstrumentType instrumentType) throws Exception {
        log.debug(String.format("Downloading instruments of type: %s", instrumentType.getCode()));
        List<MarketInstrument> marketInstruments;
        log.info(String.format("Requesting instruments of type: %s", instrumentType.getCode()));
        switch (instrumentType.getCode()) {
            case ("bond"):
                marketInstruments = tcsContextService.getBonds();
                break;
            case ("etf"):
                marketInstruments = tcsContextService.getEtfs();
                break;
            case ("stock"):
                marketInstruments = tcsContextService.getStocks();
                break;
            case ("currency"):
                marketInstruments = tcsContextService.getCurrencies();
                break;
            default:
                throw new IllegalArgumentException(String.format(
                        "Illegal instrument type: %s", instrumentType.getCode()));
        }
        List<Instrument> instruments = marketInstruments
                .stream()
                .map(marketInstrument -> modelMapper.map(marketInstrument, Instrument.class))
                .collect(Collectors.toList());
        log.info(String.format("Records received: %d", instruments.size()));
        return instruments;
    }

    public List<Candlestick> getCandlesticks(Instrument instrument, Timeframe timeframe, ZonedDateTime begPeriod, ZonedDateTime endPeriod) {
        return tcsContextService.getCandles(instrument, timeframe, begPeriod, endPeriod)
                .stream()
                .map(candle -> modelMapper.map(candle, Candlestick.class))
                .collect(Collectors.toList());
    }
}
