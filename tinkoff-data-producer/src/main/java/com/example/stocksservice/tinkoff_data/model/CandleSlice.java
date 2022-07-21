package com.example.stocksservice.tinkoff_data.model;

import lombok.Setter;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;

@Setter
public class CandleSlice implements Serializable {
    private OffsetDateTime sliceTime;
    private String interval;
    private Map<String, CandleData> candles;


    public CandleSlice(String interval, CandleData newCandle) {
        this.sliceTime = newCandle.getTime();
        this.interval = interval;
        this.candles = new HashMap<>();
        this.addNewCandleToList(newCandle);
    }

    public boolean addNewCandleToList(CandleData newCandle) {
        if(newCandle.getInterval().equals(interval) && newCandle.getTime().equals(sliceTime)) {
            candles.put(newCandle.getFigi(), newCandle);
            return true;
        }
        return false;
    }

    public OffsetDateTime getSliceTime() {
        return OffsetDateTime.of(sliceTime.toLocalDateTime(), sliceTime.getOffset());
    }

    public String getInterval() {
        return interval;
    }

    public Map<String, CandleData> getCandles() {
        return new HashMap<>(candles);
    }

    public int getCandlesSize() {
        return candles.size();
    }
}
