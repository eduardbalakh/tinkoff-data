package com.example.stocksservice.tinkoff_data.dataprovider.v2.model;

import com.example.stocksservice.tinkoff_data.utils.TcsTools;
import lombok.Getter;
import lombok.Setter;
import ru.tinkoff.piapi.contract.v1.HistoricCandle;
import ru.tinkoff.piapi.contract.v1.MarketDataResponse;

import java.math.BigDecimal;
import java.time.OffsetDateTime;


@Getter
@Setter
public class CandleData {
    private String figi;
    private BigDecimal highPrice;
    private BigDecimal lowPrice;
    private BigDecimal openPrice;
    private BigDecimal closingPrice;
    private OffsetDateTime time;
    private String interval;

    public CandleData(String figi, BigDecimal highPrice, BigDecimal lowPrice,
                      BigDecimal openPrice, BigDecimal closingPrice,
                      OffsetDateTime time, String interval) {
        this.figi = figi;
        this.highPrice = highPrice;
        this.lowPrice = lowPrice;
        this.openPrice = openPrice;
        this.closingPrice = closingPrice;
        this.time = time;
        this.interval = interval;
    }

    public CandleData (String figi, HistoricCandle hc) {
        this.figi = figi;
        this.highPrice = TcsTools.convertQuatationToBigDecimal(hc.getHigh());
        this.lowPrice = TcsTools.convertQuatationToBigDecimal(hc.getLow());
        this.openPrice = TcsTools.convertQuatationToBigDecimal(hc.getOpen());
        this.closingPrice = TcsTools.convertQuatationToBigDecimal(hc.getClose());
        this.time = TcsTools.toOffsetDateTime(hc.getTime().getSeconds());
        this.interval = "1min";
    }

    public CandleData (MarketDataResponse marketDataResponse) {
        this.figi = marketDataResponse.getCandle().getFigi();
        this.highPrice = TcsTools.convertQuatationToBigDecimal(marketDataResponse.getCandle().getHigh());
        this.lowPrice = TcsTools.convertQuatationToBigDecimal(marketDataResponse.getCandle().getLow());
        this.openPrice = TcsTools.convertQuatationToBigDecimal(marketDataResponse.getCandle().getOpen());
        this.closingPrice = TcsTools.convertQuatationToBigDecimal(marketDataResponse.getCandle().getClose());
        this.time = TcsTools.toOffsetDateTime(marketDataResponse.getCandle().getTime().getSeconds());
        this.interval = "1min";
    }

    @Override
    public String toString() {
        return "CandleData{" +
                "figi='" + figi + '\'' +
                ", highPrice=" + highPrice +
                ", lowPrice=" + lowPrice +
                ", openPrice=" + openPrice +
                ", closingPrice=" + closingPrice +
                ", time=" + time +
                ", interval='" + interval + '\'' +
                '}';
    }
}
