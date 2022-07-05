package com.example.stocksservice.tinkoff_data.config;

import com.example.stocksservice.tinkoff_data.dataprovider.v2.model.MarketInstrument;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Data
@Configuration
public class ConnectionConfig {

    @Bean
    public RedisTemplate<String, MarketInstrument> instrumentRedisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, MarketInstrument> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        return  template;
    }



}
