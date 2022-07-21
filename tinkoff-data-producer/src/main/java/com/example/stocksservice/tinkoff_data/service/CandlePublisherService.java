package com.example.stocksservice.tinkoff_data.service;

import com.example.stocksservice.tinkoff_data.model.CandleData;
import com.example.stocksservice.tinkoff_data.model.CandleSlice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CandlePublisherService {

    private CandleSlice candleSlice;

    private RedisTemplate<String, CandleData> candleDataRedisTemplate;
    private RedisTemplate<String, CandleSlice> candleSliceRedisTemplate;

    public CandlePublisherService(RedisTemplate<String, CandleData> candleDataRedisTemplate, RedisTemplate<String, CandleSlice> candleSliceRedisTemplate) {
        this.candleDataRedisTemplate = candleDataRedisTemplate;
        this.candleSliceRedisTemplate = candleSliceRedisTemplate;
    }

    public void refreshCandle(CandleData newCandle) {
        log.trace("Publishing new candle for figi: {} for time: {}", newCandle.getFigi(), newCandle.getTime());
        CandleData oldCandle = candleDataRedisTemplate.opsForValue().get("candle_" + newCandle.getFigi());
        log.trace("candle found for this figi ({}): {}", newCandle.getFigi(), oldCandle);
        candleDataRedisTemplate.opsForValue().set("candle_" + newCandle.getFigi(), newCandle);
        refreshSlice(newCandle);
    }

    private void refreshSlice(CandleData newCandle) {
        log.trace("Publishing new slice for time: {}", newCandle.getTime());
        if (candleSlice == null) {
            candleSlice = new CandleSlice(newCandle.getInterval(), newCandle);
        }
        if (newCandle.getTime().equals(candleSlice.getSliceTime())) {
            candleSlice.addNewCandleToList(newCandle);
        } else if (candleSlice.getSliceTime().isBefore(newCandle.getTime())) {
            candleSliceRedisTemplate.opsForValue().set("candleslice_" + candleSlice.getSliceTime().toEpochSecond(), candleSlice);
            log.info("CandleSlice size for time {} is {}: {}", candleSlice.getSliceTime(), candleSlice.getCandlesSize(),
                    candleSlice.getCandles().keySet());
            candleSlice = new CandleSlice(candleSlice.getInterval(), newCandle);
        }


    }
}
