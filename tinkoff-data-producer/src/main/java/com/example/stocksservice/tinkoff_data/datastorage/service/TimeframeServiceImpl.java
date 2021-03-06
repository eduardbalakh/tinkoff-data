package com.example.stocksservice.tinkoff_data.datastorage.service;

import com.example.stocksservice.tinkoff_data.datastorage.entity.v1.Timeframe;
import com.example.stocksservice.tinkoff_data.datastorage.repository.TimeframeRepository;
import com.example.stocksservice.tinkoff_data.exception.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class TimeframeServiceImpl implements TimeframeService {

    private TimeframeRepository periodRepository;

    @Override
    public List<Timeframe> getAll() {
        return periodRepository.findAll();
    }

    @Override
    public Timeframe getById(Long id) {
        return periodRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Timeframe.class, id));
    }

    @Override
    public Timeframe getByCode(String code) {
        return periodRepository.findByCodeIgnoreCase(code);
    }
}
