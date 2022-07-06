package com.example.stocksservice.tinkoff_data.dataprovider.v2.model;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
public class CandleSlice {
    private OffsetDateTime sliceTime;
    private String interval;
    private List<CandleData> candles;

    public CandleSlice(OffsetDateTime sliceTime, String interval, List<CandleData> candles) {
        this.sliceTime = sliceTime;
        this.interval = interval;
        this.candles = candles;
    }

    public CandleSlice(OffsetDateTime sliceTime, String interval) {
        this.sliceTime = sliceTime;
        this.interval = interval;
        this.candles = new ArrayList<>();
    }

    public boolean addNewCandleToList(CandleData newCandle) {
        if(newCandle.getInterval().equals(interval) && newCandle.getTime().equals(sliceTime)) {
            candles.add(newCandle);
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

    public List<CandleData> getCandles() {
        return new ArrayList<>(candles);
    }
}
