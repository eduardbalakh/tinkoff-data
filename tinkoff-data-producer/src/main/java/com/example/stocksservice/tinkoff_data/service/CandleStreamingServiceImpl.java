package com.example.stocksservice.tinkoff_data.service;

import com.example.stocksservice.tinkoff_data.dataprovider.ITinkoffApi;
import com.example.stocksservice.tinkoff_data.datastorage.service.CacheInstrumentServiceImpl;
import com.example.stocksservice.tinkoff_data.model.CandleData;
import com.example.stocksservice.tinkoff_data.model.MarketInstrument;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.DependsOn;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.tinkoff.piapi.contract.v1.MarketDataResponse;
import ru.tinkoff.piapi.contract.v1.SubscriptionInterval;
import ru.tinkoff.piapi.core.stream.StreamProcessor;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
@Slf4j
@DependsOn({"cacheInstrumentServiceImpl"})
@ConditionalOnProperty(name = "tinkoff-investment.candle.listener.active", havingValue = "true")
public class CandleStreamingServiceImpl implements CandleStreamingService {

    private List<String> figis;

    private ITinkoffApi tinkoffApi;
    private CacheInstrumentServiceImpl cacheInstrumentService;
    private CandlePublisherService candlePublisherService;

    public CandleStreamingServiceImpl(ITinkoffApi tinkoffApi, CacheInstrumentServiceImpl cacheInstrumentService, CandlePublisherService candlePublisherService) {
        this.tinkoffApi = tinkoffApi;
        this.cacheInstrumentService = cacheInstrumentService;
        this.candlePublisherService = candlePublisherService;
    }

    @PostConstruct
    public void init() {
        new Thread(this::publishCandleData, "event-listener").start();
    }


    @Override
    public void publishCandleData() {
        log.info("Starting publishing candle data.....");
        figis = cacheInstrumentService.getAll().stream()
                .filter(marketInstrument -> marketInstrument.getCurrency().equals("rub"))
                //.filter(marketInstrument -> marketInstrument.getType().equals(InstrumentType.STOCK))
                .map(MarketInstrument::getFigi)
                .collect(Collectors.toList());
        try {
            tinkoffApi.getApi().getMarketDataStreamService()
                    .newStream("candles_stream2627654654", getStreamProcessor(), onErrorConsumer())
                    .subscribeCandles(figis, SubscriptionInterval.SUBSCRIPTION_INTERVAL_ONE_MINUTE);
        } catch (Throwable throwable) {
            log.error("An error in subscriber, listener will be restarted", throwable);
            publishCandleData();
            throw throwable;
        }
    }

    @PreDestroy
    public void unsubscribeStream() {
        tinkoffApi.getApi().getMarketDataStreamService().getStreamById("candles_stream2627654654")
                .unsubscribeCandles(figis);
    }

    private StreamProcessor<MarketDataResponse> getStreamProcessor() {

        return item -> {
            log.trace("New data in streaming api: {}", item);
            if (item.hasCandle()) {
                CandleData cd = new CandleData(item);
                candlePublisherService.refreshCandle(cd);
                log.trace("candle : {}", cd);
            }
        };
    }

    @Scheduled(cron = "0 * * * * *")
    public void getHistoricCandles() {

    }

    private Consumer<Throwable> onErrorConsumer() {
        return throwable -> {
            log.error("An error in candles_stream, listener will be restarted", throwable);
            publishCandleData();
        };
    }


}
