package com.banksystem.rates.config;

import com.banksystem.rates.model.LoanRateProvider;
import com.banksystem.rates.model.LocalLoanRates;
import com.banksystem.rates.model.RemoteLoanRates;
import com.banksystem.rates.service.RateServiceClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class RateProperties {

    @Bean
    @Profile("dev")
    public LoanRateProvider devRates(@Value("${loan.rate.fixed}") Double rateFixed,
                                     @Value("${loan.rate.floating}") Double rateFloating) {
        return new LocalLoanRates(rateFixed, rateFloating);
    }

    @Bean
    @Profile("prod")
    public LoanRateProvider prodRates(RateServiceClient rateServiceClient) {
        return new RemoteLoanRates(rateServiceClient);

    }

}