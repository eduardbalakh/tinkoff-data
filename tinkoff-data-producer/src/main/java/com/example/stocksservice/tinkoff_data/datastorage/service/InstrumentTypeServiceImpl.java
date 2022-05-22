package com.example.stocksservice.tinkoff_data.datastorage.service;

import com.example.stocksservice.tinkoff_data.datastorage.entity.v1.InstrumentType;
import com.example.stocksservice.tinkoff_data.datastorage.repository.InstrumentTypeRepository;
import com.example.stocksservice.tinkoff_data.exception.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class InstrumentTypeServiceImpl implements InstrumentTypeService {

    private InstrumentTypeRepository instrumentTypeRepository;

    @Override
    public List<InstrumentType> getAll() {
        return instrumentTypeRepository.findAll();
    }

    @Override
    public InstrumentType getById(Long id) {
        return instrumentTypeRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(InstrumentType.class, id));
    }

    @Override
    public InstrumentType getByCode(String code) {
        return instrumentTypeRepository.findByCodeIgnoreCase(code);
    }
}
