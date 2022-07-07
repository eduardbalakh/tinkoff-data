package com.example.stocksservice.tinkoff_data.datastorage.service;


import com.example.stocksservice.tinkoff_data.model.MarketInstrument;

import java.util.List;

public interface StorageInstrumentService {

    List<MarketInstrument> getAll();

    MarketInstrument getById(Long id);

    MarketInstrument getByTicker(String ticker);

    MarketInstrument getByFigiOrTicker(String identifier);

    MarketInstrument getByFigi(String isin);

    void saveAllIfNotExists(List<MarketInstrument> instruments);

    void saveIfNotExists(MarketInstrument instrument);
}
