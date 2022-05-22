package com.example.stocksservice.tinkoff_data.datastorage.entity.v1;

import com.example.stocksservice.tinkoff_data.utils.DateTimeTools;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Entity
@Data
public class Candlestick {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(value = AccessLevel.PRIVATE)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "timeframe_id")
    private Timeframe timeframe;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "instrument_id")
    private Instrument instrument;

    private BigDecimal maximumValue;

    private BigDecimal minimumValue;

    private BigDecimal openedValue;

    private BigDecimal closedValue;

    private int volume;

    private ZonedDateTime since;

    @Override
    public String toString() {
        return String.format("%s [%s] %s: %.4f",
                this.instrument.getTicker(),
                this.timeframe.getCode(),
                DateTimeTools.getTimeFormatted(this.since),
                this.closedValue);
    }

}
