package com.example.stocksservice.tinkoff_data.datastorage.service;

import com.example.stocksservice.tinkoff_data.datastorage.entity.v1.Instrument;
import com.example.stocksservice.tinkoff_data.datastorage.repository.InstrumentRepository;
import com.example.stocksservice.tinkoff_data.exception.EntityNotFoundException;
import com.example.stocksservice.tinkoff_data.model.MarketInstrument;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class DataInstrumentServiceImpl implements StorageInstrumentService {

    private InstrumentRepository instrumentRepository;
    private ModelMapper modelMapper;

    @Override
    public List<MarketInstrument> getAll() {
        return instrumentRepository.findAll().stream()
                .map(e -> modelMapper.map(e, MarketInstrument.class))
                .collect(Collectors.toList());
    }

    @Override
    public MarketInstrument getById(Long id) {
        Instrument instrument = instrumentRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Instrument.class, id));
        return modelMapper.map(instrument, MarketInstrument.class);
    }

    @Override
    public MarketInstrument getByTicker(String ticker) {
        Instrument instrument = instrumentRepository.findByTickerIgnoreCase(ticker);
        return modelMapper.map(instrument, MarketInstrument.class);
    }

    @Override
    public MarketInstrument getByFigiOrTicker(String identifier) {
        Instrument instrument = instrumentRepository.findByFigiIgnoreCaseOrTickerIgnoreCase(identifier, identifier);
        return modelMapper.map(instrument, MarketInstrument.class);
    }

    @Override
    public MarketInstrument getByFigi(String figi) {
        Instrument instrument = instrumentRepository.findByFigiIgnoreCase(figi);
        return modelMapper.map(instrument, MarketInstrument.class);
    }

    @Override
    public void saveAllIfNotExists(List<MarketInstrument> instruments) {
        log.info("Saved {} instruments to DB", instruments.size());
        instruments.forEach(this::saveIfNotExists);
    }

    @Override
    @Transactional
    public void saveIfNotExists(MarketInstrument instrument) {
        Instrument inst = modelMapper.map(instrument, Instrument.class);
        if (!instrumentRepository.existsInstrumentByFigiIgnoreCase(instrument.getFigi()))
            instrumentRepository.saveAndFlush(inst);
    }

}
