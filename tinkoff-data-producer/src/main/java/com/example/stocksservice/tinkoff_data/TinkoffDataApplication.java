package com.example.stocksservice.tinkoff_data;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class TinkoffDataApplication {

    public static void main(String[] args) {

        SpringApplication.run(TinkoffDataApplication.class, args);
    }


    @EventListener
    public void handleContextRefreshEvent(ContextStartedEvent ctxStartEvt) {
        System.out.println("Context Start Event received.");
    }


}
