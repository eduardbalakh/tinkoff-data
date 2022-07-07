package com.example.stocksservice.tinkoff_data.service;

import com.example.stocksservice.tinkoff_data.dataprovider.ITinkoffApi;
import com.example.stocksservice.tinkoff_data.datastorage.entity.v1.InstrumentType;
import com.example.stocksservice.tinkoff_data.datastorage.service.InstrumentTypeServiceImpl;
import com.example.stocksservice.tinkoff_data.model.MarketInstrument;
import com.example.stocksservice.tinkoff_data.service.InstrumentService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.tinkoff.piapi.core.InstrumentsService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class InstrumentServiceImpl implements InstrumentService {

    private final ITinkoffApi tinkoffApi;
    private final ModelMapper modelMapper;
    private final InstrumentTypeServiceImpl instrumentTypeService;
    private final Map<String, MarketInstrument> instrumentByFigi = new ConcurrentHashMap<>();

    //@PostConstruct
    public void init() throws Exception {
        getInstruments();
    }


    @Override
    public List<MarketInstrument> getInstruments(@NonNull InstrumentType instrumentType) throws Exception {

        List<MarketInstrument> marketInstruments;
        InstrumentsService instrumentsService = tinkoffApi.getApi().getInstrumentsService();

        switch (instrumentType.getCode()) {
            case ("bond"):
                marketInstruments = instrumentsService.getAllBondsSync().stream()
                        .map(bond -> {
                            MarketInstrument bondMI = modelMapper.map(bond, MarketInstrument.class);
                            instrumentByFigi.put(bond.getFigi(), bondMI);
                            return bondMI;
                        }).collect(Collectors.toList());
                break;
            case ("etf"):
                marketInstruments = instrumentsService.getAllEtfsSync().stream()
                        .map(etfs -> {
                            MarketInstrument etfsMI = modelMapper.map(etfs, MarketInstrument.class);
                            instrumentByFigi.put(etfs.getFigi(), etfsMI);
                            return etfsMI;
                        }).collect(Collectors.toList());
                break;
            case ("stock"):
                marketInstruments = instrumentsService.getAllSharesSync().stream()
                        .map(share -> {
                            MarketInstrument shareMI = modelMapper.map(share, MarketInstrument.class);
                            instrumentByFigi.put(share.getFigi(), shareMI);
                            return shareMI;
                        }).collect(Collectors.toList());
                break;
            case ("currency"):

                marketInstruments = instrumentsService.getAllCurrenciesSync().stream()
                        .map(curr -> {
                            MarketInstrument currMI = modelMapper.map(curr, MarketInstrument.class);
                            instrumentByFigi.put(curr.getFigi(), currMI);
                            return currMI;
                        }).collect(Collectors.toList());
                break;
            default:
                throw new IllegalArgumentException(String.format(
                        "Illegal instrument type: %s", instrumentType.getCode()));
        }

        log.info(String.format("Records received: %d for instrument type: %s", marketInstruments.size(), instrumentType.getCode()));

        return marketInstruments;
    }

    @Override
    public List<MarketInstrument> getInstruments() throws Exception {

        log.info("Downloading all instruments");

        List<InstrumentType> instrumentTypes = instrumentTypeService.getAll();
        List<MarketInstrument> instruments = new ArrayList<>();
        for (InstrumentType instrumentType : instrumentTypes) {
            instruments.addAll(getInstruments(instrumentType));
        }
        log.info("Downloaded {} instruments", instrumentByFigi.size());

        return instruments;
    }
}
