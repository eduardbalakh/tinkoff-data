package com.example.stocksservice.tinkoff_data.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.tinkoff.invest.openapi.OpenApi;
import ru.tinkoff.invest.openapi.model.rest.SandboxRegisterRequest;
import ru.tinkoff.invest.openapi.okhttp.OkHttpOpenApi;

import java.io.IOException;

@Slf4j
@Service
public class ApiConnector implements AutoCloseable {

    private final GeneralTinkoffProfileConfig config;
    private OpenApi openApi;

    public ApiConnector(GeneralTinkoffProfileConfig config) {
        this.config = config;
    }

    public OpenApi getOpenApi() {
        if (openApi == null) {
            tryCloseConnection();
            log.info("Create TINKOFF INVEST API connection");
            openApi = new OkHttpOpenApi(config.getToken(), config.isSandboxMode());
            if (openApi.isSandboxMode()) {
                openApi.getSandboxContext().performRegistration(new SandboxRegisterRequest()).join();
            }
        }
        return openApi;
    }

    @Override
    public void close() throws IOException {
        if (openApi != null) {
            openApi.close();
            log.info("TINKOFF INVEST API connection has been closed");
        }
    }

    public void tryCloseConnection() {
        if (openApi != null) {
            try {
                close();
            } catch (IOException e) {
                log.error("UNABLE TO CLOSE OPEN API CLIENT", e);
            }
        }
    }
}