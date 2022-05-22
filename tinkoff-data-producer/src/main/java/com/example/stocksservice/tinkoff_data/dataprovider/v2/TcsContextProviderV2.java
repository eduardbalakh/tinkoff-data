package com.example.stocksservice.tinkoff_data.dataprovider.v2;

import com.example.stocksservice.tinkoff_data.dataprovider.TcsContextProvider;
import com.example.stocksservice.tinkoff_data.datastorage.entity.v1.Instrument;
import com.example.stocksservice.tinkoff_data.datastorage.entity.v1.Timeframe;
import com.google.common.util.concurrent.ListenableFuture;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.tinkoff.invest.openapi.model.rest.Candles;
import ru.tinkoff.invest.openapi.model.rest.InstrumentType;
import ru.tinkoff.invest.openapi.model.rest.MarketInstrument;
import ru.tinkoff.invest.openapi.model.rest.MarketInstrumentList;
import ru.tinkoff.piapi.contract.v1.Bond;
import ru.tinkoff.piapi.contract.v1.BondsResponse;
import ru.tinkoff.piapi.contract.v1.CurrenciesResponse;
import ru.tinkoff.piapi.contract.v1.EtfsResponse;
import ru.tinkoff.piapi.contract.v1.InstrumentStatus;
import ru.tinkoff.piapi.contract.v1.InstrumentsRequest;
import ru.tinkoff.piapi.contract.v1.InstrumentsServiceGrpc;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Component
@Slf4j
public class TcsContextProviderV2 implements TcsContextProvider {

    @GrpcClient("instruments-service")
    private InstrumentsServiceGrpc.InstrumentsServiceFutureStub futureStub;

    private final ModelMapper modelMapper;

    public TcsContextProviderV2(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    @Override
    public MarketInstrumentList getStocks() {
        return null;
    }

    @Override
    public MarketInstrumentList getBonds() {
        ListenableFuture<BondsResponse> bondsFuture = futureStub.bonds(InstrumentsRequest.newBuilder()
                .setInstrumentStatus(InstrumentStatus.INSTRUMENT_STATUS_ALL)
                .build());
        BondsResponse bondsResponse = processResponse(bondsFuture, InstrumentType.BOND);
        MarketInstrumentList list = new MarketInstrumentList();
        List<MarketInstrument> listOfInstruments = bondsResponse.getInstrumentsList().stream()
                .map(bond -> modelMapper.map(bond, MarketInstrument.class))
                .collect(Collectors.toList());
        list.setInstruments(listOfInstruments);
        list.setTotal(BigDecimal.valueOf(bondsResponse.getInstrumentsList().size()));
        return list;
    }

    @Override
    public MarketInstrumentList getEtfs() {
        ListenableFuture<EtfsResponse> etfsFuture = futureStub.etfs(InstrumentsRequest.newBuilder()
                .setInstrumentStatus(InstrumentStatus.INSTRUMENT_STATUS_ALL)
                .build());
        EtfsResponse etfsResponse = processResponse(etfsFuture, InstrumentType.ETF);
        MarketInstrumentList list = new MarketInstrumentList();
        List<MarketInstrument> listOfInstruments = etfsResponse.getInstrumentsList().stream()
                .map(bond -> modelMapper.map(bond, MarketInstrument.class))
                .collect(Collectors.toList());
        list.setInstruments(listOfInstruments);
        list.setTotal(BigDecimal.valueOf(etfsResponse.getInstrumentsList().size()));
        return list;
    }

    @Override
    public MarketInstrumentList getCurrencies() {
        ListenableFuture<CurrenciesResponse> etfsFuture = futureStub.currencies(InstrumentsRequest.newBuilder()
                .setInstrumentStatus(InstrumentStatus.INSTRUMENT_STATUS_ALL)
                .build());
        CurrenciesResponse etfsResponse = processResponse(etfsFuture, InstrumentType.ETF);
        MarketInstrumentList list = new MarketInstrumentList();
        List<MarketInstrument> listOfInstruments = etfsResponse.getInstrumentsList().stream()
                .map(bond -> modelMapper.map(bond, MarketInstrument.class))
                .collect(Collectors.toList());
        list.setInstruments(listOfInstruments);
        list.setTotal(BigDecimal.valueOf(etfsResponse.getInstrumentsList().size()));
        return list;
    }

    @Override
    public Optional<Candles> getCandles(Instrument instrument, Timeframe timeframe, ZonedDateTime begPeriod, ZonedDateTime endPeriod) {
        return Optional.empty();
    }

    private <V> V processResponse(ListenableFuture<V> inputFuture, InstrumentType type) {
        V response = null;
        try {
            response = inputFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            log.error("Exception while extracting " + type.toString(), e);
        }
        if (response == null) {
            log.debug("Response is null. Instrument type: " + type.toString());
            throw new RuntimeException("Response is null. Instrument type:" + type);
        }
        return response;
    }
}
