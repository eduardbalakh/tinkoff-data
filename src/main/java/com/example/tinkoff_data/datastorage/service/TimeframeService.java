package com.example.tinkoff_data.datastorage.service;

import com.example.tinkoff_data.datastorage.entity.v1.Timeframe;

import java.util.List;

public interface TimeframeService {

    List<Timeframe> getAll();

    Timeframe getById(Long id);

    Timeframe getByCode(String code);
}
