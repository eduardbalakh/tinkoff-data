package com.example.tinkoff_data;

import com.example.tinkoff_data.config.ApiConnector;
import com.example.tinkoff_data.config.GeneralTinkoffProfileConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TinkoffDataApplication {

    public static void main(String[] args) {

        SpringApplication.run(TinkoffDataApplication.class, args);
    }

}
