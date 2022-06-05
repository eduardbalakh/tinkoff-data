package com.example.stocksservice.tinkoff_data.dataprovider.v2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.tinkoff.piapi.contract.v1.Account;
import ru.tinkoff.piapi.contract.v1.AccountType;
import ru.tinkoff.piapi.core.InvestApi;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
@Slf4j
public class TcsContextProviderV2_1 {

    private InvestApi investApi;

    @Value("${tinkoff-investment.sandboxMode}")
    private Boolean isSandboxMode;

    @Value("${tinkoff-investment.token}")
    private String token;

    @Value("${tinkoff-investment.accountId}")
    private String accountId;

    @Value("${tinkoff-investment.applicationName}")
    private String applicationName;

    public InvestApi getApi() {
        return investApi;
    }

    @PostConstruct
    public void init() {
        investApi = isSandboxMode ?
                InvestApi.createSandbox(token) :
                InvestApi.create(token, applicationName);

        List<Account> accounts = isSandboxMode ?
                investApi.getSandboxService().getAccountsSync() : investApi.getUserService().getAccountsSync();

        log.info("Available accounts: {}", accounts.size());

        accounts.forEach(account -> log.info("AccountId: {}, name: {}", account.getId(), account.getName()));
        Account account = accounts.stream()
                .filter(a -> a.getType() == AccountType.ACCOUNT_TYPE_TINKOFF)
                .filter(a -> StringUtils.isEmpty(accountId) || accountId.equals(a.getId()))
                .findFirst().orElseThrow(() -> new RuntimeException("Account not found for token provided"));
        accountId = account.getId();

    }








}
