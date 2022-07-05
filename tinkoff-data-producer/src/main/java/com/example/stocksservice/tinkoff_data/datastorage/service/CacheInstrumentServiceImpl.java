package com.example.stocksservice.tinkoff_data.datastorage.service;

import com.example.stocksservice.tinkoff_data.dataprovider.v2.model.MarketInstrument;
import com.example.stocksservice.tinkoff_data.datastorage.entity.v1.Instrument;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class CacheInstrumentServiceImpl implements InstrumentService {

    private RedisTemplate<String, MarketInstrument> instrumentRedisTemplate;

    @PostConstruct
    public void init() {
        String pong = instrumentRedisTemplate.getConnectionFactory().getConnection().ping();
        log.info("Redis connection: {}", pong);
    }

    @Override
    public List<Instrument> getAll() {
        return null;
    }

    @Override
    public Instrument getById(Long id) {
        return null;
    }

    @Override
    public Instrument getByTicker(String ticker) {
        return null;
    }

    @Override
    public Instrument getByFigiOrTicker(String identifier) {
        return null;
    }

    @Override
    public Instrument getByFigi(String isin) {
        return null;
    }

    @Override
    public void saveAllIfNotExists(List<Instrument> instruments) {

    }

    @Override
    public void saveIfNotExists(Instrument instrument) {

    }
}
