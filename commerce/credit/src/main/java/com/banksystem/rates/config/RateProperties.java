package com.banksystem.rates.config;

import com.banksystem.rates.model.CreditRates;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestTemplate;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Configuration
public class RateProperties {

    @Bean
    @Profile("dev")
    public CreditRates devRates(final @Value("${credit.rate.fixed}") Double rateFixed,
                                final @Value("${credit.rate.floating}") Double rateFloating){
        return new CreditRates(rateFixed, rateFloating);
    }

    @Bean
    @Profile("prod")
    public CreditRates prodRates(final RestTemplateBuilder restTemplateBuilder,
                                 final @Value("${credit.rateProvider}") String rateProvider){
        final RestTemplate restTemplate = restTemplateBuilder
                .defaultHeader(CONTENT_TYPE, APPLICATION_JSON.toString())
                .rootUri(rateProvider)
                .build();
        return restTemplate.getForEntity("/credit", CreditRates.class).getBody();
    }


}