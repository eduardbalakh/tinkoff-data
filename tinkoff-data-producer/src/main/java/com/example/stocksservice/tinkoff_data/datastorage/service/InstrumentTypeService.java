package com.example.stocksservice.tinkoff_data.datastorage.service;

import com.example.stocksservice.tinkoff_data.datastorage.entity.v1.InstrumentType;

import java.util.List;

public interface InstrumentTypeService {

    List<InstrumentType> getAll();

    InstrumentType getById(Long id);

    InstrumentType getByCode(String code);


}
