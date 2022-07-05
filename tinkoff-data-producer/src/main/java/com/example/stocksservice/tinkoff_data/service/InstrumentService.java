package com.example.stocksservice.tinkoff_data.service;


import com.example.stocksservice.tinkoff_data.dataprovider.v2.model.MarketInstrument;
import com.example.stocksservice.tinkoff_data.datastorage.entity.v1.InstrumentType;
import lombok.NonNull;

import java.util.List;

public interface InstrumentService {

    List<MarketInstrument> getInstruments(@NonNull InstrumentType instrumentType) throws Exception;

    List<MarketInstrument> getInstruments() throws Exception;


}
