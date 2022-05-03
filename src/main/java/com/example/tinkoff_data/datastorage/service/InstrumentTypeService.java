package com.example.tinkoff_data.datastorage.service;

import com.example.tinkoff_data.datastorage.entity.InstrumentType;

import java.util.List;

public interface InstrumentTypeService {

    List<InstrumentType> getAll();

    InstrumentType getById(Long id);

    InstrumentType getByCode(String code);


}
