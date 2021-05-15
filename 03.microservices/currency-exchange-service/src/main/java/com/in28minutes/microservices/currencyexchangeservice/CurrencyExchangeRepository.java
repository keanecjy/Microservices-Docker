package com.in28minutes.microservices.currencyexchangeservice;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyExchangeRepository
        extends JpaRepository<CurrencyExchange, Long> {

    // Matches the From + To
    CurrencyExchange findByFromAndTo(String from, String to);
}
