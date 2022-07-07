package com.example.stocksservice.tinkoff_data.datastorage.service;

import com.example.stocksservice.tinkoff_data.exception.RedisConnectionException;
import com.example.stocksservice.tinkoff_data.model.MarketInstrument;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.util.ReflectionTestUtils;
import ru.tinkoff.invest.openapi.model.rest.InstrumentType;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CacheInstrumentServiceImplTest {

    private CacheInstrumentServiceImpl service;

    private RedisTemplate<String, MarketInstrument> instrumentRedisTemplate = mock(RedisTemplate.class, Mockito.RETURNS_DEEP_STUBS);
    private List<MarketInstrument> marketInstruments = new ArrayList<>();
    private String tinkoffInstrumentsTopic = "test_instruments";

    @BeforeEach
    void init() {
        service = new CacheInstrumentServiceImpl(instrumentRedisTemplate);
        ReflectionTestUtils.setField(service, "tinkoffInstrumentsTopic", tinkoffInstrumentsTopic);
        MarketInstrument mi1 = new MarketInstrument();
        mi1.setCurrency("usd");
        mi1.setFigi("00001");
        mi1.setName("Share1");
        mi1.setType(InstrumentType.STOCK);
        mi1.setTicker("ticker1");
        MarketInstrument mi2 = new MarketInstrument();
        mi2.setCurrency("rub");
        mi2.setFigi("00002");
        mi2.setName("ETF1");
        mi2.setType(InstrumentType.ETF);
        mi2.setTicker("ticker2");
        marketInstruments.addAll(List.of(mi1, mi2));
    }

    @Test
    void testGetAll_ShouldPass() {
        // given
        given(instrumentRedisTemplate.opsForList().size(tinkoffInstrumentsTopic))
                .willReturn(Long.valueOf(2));
        given(instrumentRedisTemplate.opsForList().range(tinkoffInstrumentsTopic, 0, 2))
                .willReturn(marketInstruments);

        // when
        List<MarketInstrument> result = service.getAll();

        // then
        assertEquals(2, result.size());
        assertEquals(result, marketInstruments);
    }

    @Test
    void testGetAll_ShouldThrowIllegalStateException() {
        // given
        given(instrumentRedisTemplate.opsForList().size(tinkoffInstrumentsTopic))
                .willReturn(null);

        // when - then
        assertThrows(IllegalStateException.class, () -> service.getAll());
    }

    @Test
    public void testSaveIfNotExists_ShouldThrowUnsupportedOperationException() {
        // when - then
        assertThrows(UnsupportedOperationException.class, () -> service.saveIfNotExists(null));
    }

    @Test
    public void testSaveAllIfNotExists_KeyExists_ShouldPass() {
        // given
        given(instrumentRedisTemplate.countExistingKeys(List.of(tinkoffInstrumentsTopic)))
                .willReturn(1L);

        // when
        service.saveAllIfNotExists(marketInstruments);

        // then
        verify(instrumentRedisTemplate, times(1)).delete(tinkoffInstrumentsTopic);
        verify(instrumentRedisTemplate.opsForList(), times(1))
                .rightPushAll(tinkoffInstrumentsTopic, marketInstruments);
    }

    @Test
    public void testSaveAllIfNotExists_KeyNotExists_ShouldPass() {
        // given
        given(instrumentRedisTemplate.countExistingKeys(List.of(tinkoffInstrumentsTopic)))
                .willReturn(0L);

        // when
        service.saveAllIfNotExists(marketInstruments);

        // then
        verify(instrumentRedisTemplate, times(0)).delete(tinkoffInstrumentsTopic);
        verify(instrumentRedisTemplate.opsForList(), times(1))
                .rightPushAll(tinkoffInstrumentsTopic, marketInstruments);
    }

    @Test
    public void testGetByFigi_FullTest() {
        // given
        given(instrumentRedisTemplate.opsForList().size(tinkoffInstrumentsTopic))
                .willReturn(Long.valueOf(2));
        given(instrumentRedisTemplate.opsForList().range(tinkoffInstrumentsTopic, 0, 2))
                .willReturn(marketInstruments);

        // when
        MarketInstrument mi1 = service.getByFigi("00001");
        MarketInstrument mi2 = service.getByFigi("00002");
        MarketInstrument mi3 = service.getByFigi("NOT_EXISTS");

        // then
        assertEquals(mi1, marketInstruments.get(0));
        assertEquals(mi2, marketInstruments.get(1));
        assertNull(mi3);
    }

    @Test
    public void testGetByTicker_FullTest() {
        // given
        given(instrumentRedisTemplate.opsForList().size(tinkoffInstrumentsTopic))
                .willReturn(Long.valueOf(2));
        given(instrumentRedisTemplate.opsForList().range(tinkoffInstrumentsTopic, 0, 2))
                .willReturn(marketInstruments);

        // when
        MarketInstrument mi1 = service.getByTicker("ticker1");
        MarketInstrument mi2 = service.getByTicker("ticker2");
        MarketInstrument mi3 = service.getByTicker("NOT_EXISTS");

        // then
        assertEquals(mi1, marketInstruments.get(0));
        assertEquals(mi2, marketInstruments.get(1));
        assertNull(mi3);
    }

    @Test
    public void testGetByFigiOrTicker_FullTest() {
        // given
        given(instrumentRedisTemplate.opsForList().size(tinkoffInstrumentsTopic))
                .willReturn(Long.valueOf(2));
        given(instrumentRedisTemplate.opsForList().range(tinkoffInstrumentsTopic, 0, 2))
                .willReturn(marketInstruments);

        // when
        MarketInstrument mi1ByTicker = service.getByTicker("ticker1");
        MarketInstrument mi2ByTicker = service.getByTicker("ticker2");
        MarketInstrument mi3ByTicker = service.getByTicker("NOT_EXISTS");
        MarketInstrument mi1ByFigi = service.getByFigi("00001");
        MarketInstrument mi2ByFigi = service.getByFigi("00002");
        MarketInstrument mi3ByFigi = service.getByFigi("NOT_EXISTS");

        // then
        assertEquals(mi1ByTicker, mi1ByFigi);
        assertEquals(mi2ByTicker, mi2ByFigi);
        assertNull(mi3ByTicker);
        assertNull(mi3ByFigi);
    }

    @Test
    public void testGetById_ShouldThrowUnsupportedOperationException() {
        // when - then
        assertThrows(UnsupportedOperationException.class, () -> service.getById(1L));
    }

    @Test
    public void initMethodTest_ShouldNotConnected() {
        // given
        given(instrumentRedisTemplate.getConnectionFactory().getConnection().ping())
                .willReturn("nothing");

        // when - then
        assertThrows(RedisConnectionException.class, () -> service.init());
    }

    @Test
    public void initMethodTest_ShouldConnect() {
        // given
        given(instrumentRedisTemplate.getConnectionFactory().getConnection().ping())
                .willReturn("PONG");

        // when - then
        assertDoesNotThrow(() -> service.init());
    }




}