package com.example.tinkoff_data.datastorage.service;

import com.example.tinkoff_data.datastorage.entity.v1.Instrument;

import java.util.List;

public interface InstrumentService {

    List<Instrument> getAll();

    Instrument getById(Long id);

    Instrument getByTicker(String ticker);

    Instrument getByFigiOrTicker(String identifier);

    Instrument getByFigi(String isin);

    void saveAllIfNotExists(List<Instrument> instruments);

    void saveIfNotExists(Instrument instrument);
}
