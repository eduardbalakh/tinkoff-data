package com.example.tinkoff_data.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class GeneralTinkoffProfileConfig {

    private final static int ARGUMENTS_NUMBER = 2;

    @Value("${tinkoff-investment.token}")
    private String token;
    @Value("${tinkoff-investment.sandboxMode}")
    private boolean sandboxMode;

    public GeneralTinkoffProfileConfig() {}

    public GeneralTinkoffProfileConfig(String[] args) {
        if (args.length < 2)
            throw new IllegalArgumentException(String.format(
                    "Invalid number of arguments [%d], expected [%d]",
                    args.length, ARGUMENTS_NUMBER));
        setParameters(args[0], Boolean.parseBoolean(args[1]));
    }

    public GeneralTinkoffProfileConfig(String token, boolean sandBoxMode) {
        setParameters(token, sandBoxMode);
    }

    private void setParameters(String token, boolean sandBoxMode) {
        this.token = token;
        this.sandboxMode = sandBoxMode;
    }

    @Override
    public final String toString() {
        return String.format("core.Parameters: sandBoxMode = %s", sandboxMode ? "true" : "false");
    }

}
