package com.example.stocksservice.tinkoff_data.config;


import com.example.stocksservice.tinkoff_data.model.CandleData;
import com.example.stocksservice.tinkoff_data.model.CandleSlice;
import com.example.stocksservice.tinkoff_data.model.MarketInstrument;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;

@Data
@Configuration
@EnableScheduling
public class ConnectionConfig {

    @Bean
    public RedisTemplate<String, MarketInstrument> instrumentRedisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, MarketInstrument> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        return  template;
    }

    @Bean
    public RedisTemplate<String, CandleSlice> candleSliceRedisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, CandleSlice> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        return template;
    }

    @Bean
    public RedisTemplate<String, CandleData> candleDataRedisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, CandleData> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        return template;
    }



}
