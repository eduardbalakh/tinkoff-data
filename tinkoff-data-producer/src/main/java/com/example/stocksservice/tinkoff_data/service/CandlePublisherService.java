package com.example.stocksservice.tinkoff_data.service;

import com.example.stocksservice.tinkoff_data.model.CandleData;
import com.example.stocksservice.tinkoff_data.model.CandleSlice;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class CandlePublisherService {

    private RedisTemplate<String, CandleData> candleDataRedisTemplate;
    private RedisTemplate<String, CandleSlice> candleSliceRedisTemplate;

    public void refreshCandle(CandleData newCandle) {
        log.trace("Publishing new candle for figi: {} for time: {}", newCandle.getFigi(), newCandle.getTime());
        CandleData oldCandle = candleDataRedisTemplate.opsForValue().get("candle_" + newCandle.getFigi());
        log.info("candle found for this figi ({}): {}", newCandle.getFigi(), oldCandle);
        candleDataRedisTemplate.opsForValue().set("candle_" + newCandle.getFigi(), newCandle);
    }

    public void refreshSlice(CandleSlice newSlice) {
        log.trace("Publishing new candle for figi: {}", newSlice.getSliceTime());
        throw new UnsupportedOperationException("Not implemented yet");
    }



}
