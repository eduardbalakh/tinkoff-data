package com.example.stocksservice.tinkoff_data.datastorage.service;

import com.example.stocksservice.tinkoff_data.dataprovider.v2.model.MarketInstrument;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Service
@Slf4j
public class CacheInstrumentServiceImpl implements StorageInstrumentService {

    private RedisTemplate<String, MarketInstrument> instrumentRedisTemplate;

    public CacheInstrumentServiceImpl(RedisTemplate<String, MarketInstrument> instrumentRedisTemplate) {
        this.instrumentRedisTemplate = instrumentRedisTemplate;
    }

    @Value("${tinkoff-investment.redis.topic.instruments}")
    private String tinkoffInstrumentsTopic;

    @PostConstruct
    public void init() {
        String pong = instrumentRedisTemplate.getConnectionFactory().getConnection().ping();
        log.info("Redis connection: {}", pong);
    }

    @Override
    public List<MarketInstrument> getAll() {
        Long size = instrumentRedisTemplate.opsForList().size(tinkoffInstrumentsTopic);
        if(size == null) {
            throw new IllegalStateException("Redis key does not exists for instruments");
        }
        return instrumentRedisTemplate.opsForList().range(tinkoffInstrumentsTopic, 0, size);
    }

    @Override
    public MarketInstrument getById(Long id) {
        throw new UnsupportedOperationException("Cannot get by Id from redis");
    }

    @Override
    public MarketInstrument getByTicker(String ticker) {
        return getAll().stream().filter(s -> s.getTicker().equals(ticker)).findFirst().orElse(null);
    }

    @Override
    public MarketInstrument getByFigiOrTicker(String identifier) {
        List<MarketInstrument> allInstruments = getAll();
        MarketInstrument byTicker = allInstruments.stream()
                .filter(s -> s.getTicker().equals(identifier)).findFirst().orElse(null);
        MarketInstrument byFigi = allInstruments.stream()
                .filter(s -> s.getFigi().equals(identifier)).findFirst().orElse(null);
        return Stream.of(byFigi, byTicker).filter(Objects::nonNull).findFirst().orElse(null);
    }

    @Override
    public MarketInstrument getByFigi(String isin) {
        return getAll().stream().filter(s -> s.getFigi().equals(isin)).findFirst().orElse(null);
    }

    @Override
    public void saveAllIfNotExists(List<MarketInstrument> instruments) {

        if (instrumentRedisTemplate.countExistingKeys(List.of(tinkoffInstrumentsTopic)).intValue() != 0) {
            instrumentRedisTemplate.delete(tinkoffInstrumentsTopic);
        }
        instrumentRedisTemplate.opsForList().rightPushAll(tinkoffInstrumentsTopic, instruments);
        log.info("Saved {} instruments to Redis: {}", instruments.size(), tinkoffInstrumentsTopic);
    }

    @Override
    public void saveIfNotExists(MarketInstrument instrument) {
        throw new UnsupportedOperationException("SaveIfNotExists for one object for Redis is not implemented yet");
    }
}
