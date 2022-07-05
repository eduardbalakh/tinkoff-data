package com.example.stocksservice.tinkoff_data.datastorage.service;

import com.example.stocksservice.tinkoff_data.datastorage.entity.v1.Instrument;
import com.example.stocksservice.tinkoff_data.datastorage.repository.InstrumentRepository;
import com.example.stocksservice.tinkoff_data.exception.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
public class DataInstrumentServiceImpl implements InstrumentService {

    private InstrumentRepository instrumentRepository;

    @Override
    public List<Instrument> getAll() {
        return instrumentRepository.findAll();
    }

    @Override
    public Instrument getById(Long id) {
        return instrumentRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Instrument.class, id));
    }

    @Override
    public Instrument getByTicker(String ticker) {
        return instrumentRepository.findByTickerIgnoreCase(ticker);
    }

    @Override
    public Instrument getByFigiOrTicker(String identifier) {
        return instrumentRepository.findByFigiIgnoreCaseOrTickerIgnoreCase(identifier, identifier);
    }

    @Override
    public Instrument getByFigi(String figi) {
        return instrumentRepository.findByFigiIgnoreCase(figi);
    }

    @Override
    public void saveAllIfNotExists(List<Instrument> instruments) {
        instruments.forEach(this::saveIfNotExists);
    }

    @Override
    @Transactional
    public void saveIfNotExists(Instrument instrument) {
        if (!instrumentRepository.existsInstrumentByFigiIgnoreCase(instrument.getFigi()))
            instrumentRepository.saveAndFlush(instrument);
    }

}
