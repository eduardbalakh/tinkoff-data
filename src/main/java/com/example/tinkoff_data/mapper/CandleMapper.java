package com.example.tinkoff_data.mapper;

import com.example.tinkoff_data.datastorage.entity.Candlestick;
import com.example.tinkoff_data.datastorage.entity.Instrument;
import com.example.tinkoff_data.datastorage.entity.Timeframe;
import com.example.tinkoff_data.datastorage.service.InstrumentService;
import com.example.tinkoff_data.datastorage.service.InstrumentTypeService;
import com.example.tinkoff_data.datastorage.service.TimeframeService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;
import ru.tinkoff.invest.openapi.model.rest.Candle;
import ru.tinkoff.invest.openapi.model.rest.CandleResolution;
import ru.tinkoff.invest.openapi.model.rest.MarketInstrument;

import javax.annotation.PostConstruct;

@Component
@Data
@AllArgsConstructor
public class CandleMapper {

    private final ModelMapper modelMapper;
    private final InstrumentTypeService instrumentTypeService;
    private final InstrumentService instrumentService;
    private final TimeframeService timeFrameService;

    @PostConstruct
    public void init() {

        modelMapper.createTypeMap(CandleResolution.class, Timeframe.class).setConverter(new Converter<CandleResolution, Timeframe>() {
            @Override
            public Timeframe convert(MappingContext<CandleResolution, Timeframe> mappingContext) {
                String timeframeCode;
                switch (mappingContext.getSource()) {
                    case _1MIN:
                        timeframeCode = "MIN1";
                        break;
                    case _2MIN:
                        timeframeCode = "MIN2";
                        break;
                    case _3MIN:
                        timeframeCode = "MIN3";
                        break;
                    case _5MIN:
                        timeframeCode = "MIN5";
                        break;
                    case _10MIN:
                        timeframeCode = "MIN10";
                        break;
                    case _15MIN:
                        timeframeCode = "MIN15";
                        break;
                    case _30MIN:
                        timeframeCode = "MIN30";
                        break;
                    case HOUR:
                        timeframeCode = "HOUR1";
                        break;
                    case DAY:
                        timeframeCode = "DAY1";
                        break;
                    case WEEK:
                        timeframeCode = "WEEK1";
                        break;
                    case MONTH:
                        timeframeCode = "MON1";
                        break;
                    default:
                        timeframeCode = null;
                }
                return timeFrameService.getByCode(timeframeCode);
            }
        });

        modelMapper.createTypeMap(Timeframe.class, CandleResolution.class).setConverter(new Converter<Timeframe, CandleResolution>() {
            @Override
            public CandleResolution convert(MappingContext<Timeframe, CandleResolution> mappingContext) {
                switch (mappingContext.getSource().getCode()) {
                    case "MIN1":
                        return CandleResolution._1MIN;
                    case "MIN2":
                        return CandleResolution._2MIN;
                    case "MIN3":
                        return CandleResolution._3MIN;
                    case "MIN5":
                        return CandleResolution._5MIN;
                    case "MIN10":
                        return CandleResolution._10MIN;
                    case "MIN15":
                        return CandleResolution._15MIN;
                    case "MIN30":
                        return CandleResolution._30MIN;
                    case "HOUR1":
                        return CandleResolution.HOUR;
                    case "DAY1":
                        return CandleResolution.DAY;
                    case "WEEK1":
                        return CandleResolution.WEEK;
                    case "MONTH1":
                        return CandleResolution.MONTH;
                    default:
                        return null;
                }
            }
        });

        modelMapper.createTypeMap(Candle.class, Candlestick.class).setConverter(new Converter<Candle, Candlestick>() {
            @Override
            public Candlestick convert(MappingContext<Candle, Candlestick> mappingContext) {
                Candle candle = mappingContext.getSource();
                Candlestick candlestick = new Candlestick();
                candlestick.setTimeframe(modelMapper.map(candle.getInterval(), Timeframe.class));
                candlestick.setInstrument(instrumentService.getByFigi(candle.getFigi()));
                candlestick.setSince(candle.getTime().toZonedDateTime());
                candlestick.setOpenedValue(candle.getO());
                candlestick.setClosedValue(candle.getC());
                candlestick.setMaximumValue(candle.getH());
                candlestick.setMinimumValue(candle.getL());
                candlestick.setVolume(candle.getV());
                return candlestick;
            }
        });

        modelMapper.createTypeMap(MarketInstrument.class, Instrument.class).setConverter(new Converter<MarketInstrument, Instrument>() {
            @Override
            public Instrument convert(MappingContext<MarketInstrument, Instrument> mappingContext) {
                Instrument instrument = new Instrument();
                MarketInstrument marketInstrument = mappingContext.getSource();
                instrument.setCurrency(marketInstrument.getCurrency().getValue());
                instrument.setTicker(marketInstrument.getTicker());
                instrument.setFigi(marketInstrument.getFigi());
                instrument.setIsin(marketInstrument.getIsin());
                instrument.setName(marketInstrument.getName());
                instrument.setLot(marketInstrument.getLot());
                instrument.setIncrement(marketInstrument.getMinPriceIncrement());
                instrument.setInstrumentType(instrumentTypeService.getByCode(marketInstrument.getType().toString()));
                return instrument;
            }
        });
    }
}
