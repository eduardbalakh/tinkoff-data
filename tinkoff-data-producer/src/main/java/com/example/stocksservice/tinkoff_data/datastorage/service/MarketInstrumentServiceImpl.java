package com.example.stocksservice.tinkoff_data.datastorage.service;

import com.example.stocksservice.tinkoff_data.dataprovider.v2.model.MarketInstrument;
import com.example.stocksservice.tinkoff_data.datastorage.entity.v1.InstrumentType;
import com.example.stocksservice.tinkoff_data.service.InstrumentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class MarketInstrumentServiceImpl implements MarketInstrumentService {

    private StorageInstrumentService dataInstrumentServiceImpl;
    private StorageInstrumentService cacheInstrumentServiceImpl;
    private InstrumentTypeService instrumentTypeService;
    private CandlestickService candlestickService;
    private InstrumentService instrumentService;


    @Override
    public void updateInstruments() throws Exception {
        log.info("Updating instruments");
        List<MarketInstrument> instruments = instrumentService.getInstruments();
        dataInstrumentServiceImpl.saveAllIfNotExists(instruments);
        cacheInstrumentServiceImpl.saveAllIfNotExists(instruments);
        log.info(String.format("Updating instruments: Records processed: %d", instruments.size()));
    }

    @Override
    public void updateInstruments(String instrumentTypeCode) throws Exception {
        InstrumentType instrumentType = instrumentTypeService.getByCode(instrumentTypeCode);
        if (instrumentType == null) throw new IllegalArgumentException(String.format(
                "Illegal instrument type: %s", instrumentTypeCode));
        updateInstruments(instrumentType);
    }

    @Override
    public void updateInstruments(InstrumentType instrumentType) throws Exception {
        log.info(String.format("Updating instruments of type: %s", instrumentType.getCode()));
        List<MarketInstrument> instruments = instrumentService.getInstruments(instrumentType);
        dataInstrumentServiceImpl.saveAllIfNotExists(instruments);
        cacheInstrumentServiceImpl.saveAllIfNotExists(instruments);
        log.info(String.format("Updating instruments: Records processed: %d", instruments.size()));
    }
}
