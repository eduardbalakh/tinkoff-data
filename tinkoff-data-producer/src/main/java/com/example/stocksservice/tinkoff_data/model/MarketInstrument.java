package com.example.stocksservice.tinkoff_data.model;

import ru.tinkoff.invest.openapi.model.rest.InstrumentType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class MarketInstrument implements Serializable {

    private String figi = null;

    private String ticker = null;

    private String isin = null;

    private BigDecimal minPriceIncrement = null;

    private Integer lot = null;

    private Integer minQuantity = null;

    private String currency = null;

    private String name = null;

    private InstrumentType type = null;

    public MarketInstrument() {
    }

    public MarketInstrument figi(String figi) {
        this.figi = figi;
        return this;
    }

    public String getFigi() {
        return this.figi;
    }

    public void setFigi(String figi) {
        this.figi = figi;
    }

    public MarketInstrument ticker(String ticker) {
        this.ticker = ticker;
        return this;
    }

    public String getTicker() {
        return this.ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public MarketInstrument isin(String isin) {
        this.isin = isin;
        return this;
    }

    public String getIsin() {
        return this.isin;
    }

    public void setIsin(String isin) {
        this.isin = isin;
    }

    public MarketInstrument minPriceIncrement(BigDecimal minPriceIncrement) {
        this.minPriceIncrement = minPriceIncrement;
        return this;
    }

    public BigDecimal getMinPriceIncrement() {
        return this.minPriceIncrement;
    }

    public void setMinPriceIncrement(BigDecimal minPriceIncrement) {
        this.minPriceIncrement = minPriceIncrement;
    }

    public MarketInstrument lot(Integer lot) {
        this.lot = lot;
        return this;
    }

    public Integer getLot() {
        return this.lot;
    }

    public void setLot(Integer lot) {
        this.lot = lot;
    }

    public MarketInstrument minQuantity(Integer minQuantity) {
        this.minQuantity = minQuantity;
        return this;
    }

    public Integer getMinQuantity() {
        return this.minQuantity;
    }

    public void setMinQuantity(Integer minQuantity) {
        this.minQuantity = minQuantity;
    }

    public MarketInstrument currency(String currency) {
        this.currency = currency;
        return this;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public MarketInstrument name(String name) {
        this.name = name;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MarketInstrument type(InstrumentType type) {
        this.type = type;
        return this;
    }

    public InstrumentType getType() {
        return this.type;
    }

    public void setType(InstrumentType type) {
        this.type = type;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            MarketInstrument marketInstrument = (MarketInstrument)o;
            return Objects.equals(this.figi, marketInstrument.figi) && Objects.equals(this.ticker, marketInstrument.ticker) && Objects.equals(this.isin, marketInstrument.isin) && Objects.equals(this.minPriceIncrement, marketInstrument.minPriceIncrement) && Objects.equals(this.lot, marketInstrument.lot) && Objects.equals(this.minQuantity, marketInstrument.minQuantity) && Objects.equals(this.currency, marketInstrument.currency) && Objects.equals(this.name, marketInstrument.name) && Objects.equals(this.type, marketInstrument.type);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hash(this.figi, this.ticker, this.isin, this.minPriceIncrement, this.lot, this.minQuantity, this.currency, this.name, this.type);
    }

    public String toString() {
        return "class MarketInstrument {\n" +
                "    figi: " + this.toIndentedString(this.figi) + "\n" +
                "    ticker: " + this.toIndentedString(this.ticker) + "\n" +
                "    isin: " + this.toIndentedString(this.isin) + "\n" +
                "    minPriceIncrement: " + this.toIndentedString(this.minPriceIncrement) + "\n" +
                "    lot: " + this.toIndentedString(this.lot) + "\n" +
                "    minQuantity: " + this.toIndentedString(this.minQuantity) + "\n" +
                "    currency: " + this.toIndentedString(this.currency) + "\n" +
                "    name: " + this.toIndentedString(this.name) + "\n" +
                "    type: " + this.toIndentedString(this.type) + "\n" +
                "}";
    }

    private String toIndentedString(Object o) {
        return o == null ? "null" : o.toString().replace("\n", "\n    ");
    }
}
