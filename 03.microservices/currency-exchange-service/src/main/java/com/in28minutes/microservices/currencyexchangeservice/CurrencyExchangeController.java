package com.in28minutes.microservices.currencyexchangeservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyExchangeController {

    private final Logger logger = LoggerFactory.getLogger(CurrencyExchangeController.class);

    @Autowired
    private CurrencyExchangeRepository repository;

    // Environment provided by spring boot
    @Autowired
    private Environment environment;

    // Ideally we use:
    // @GetMapping("/currency-exchange")
    // retrieveExchangeValue(@RequestParam String from, @RequestParam String to)
    // to get an URI of: /currency-exchange?from={from}&to={to}
    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public CurrencyExchange retrieveExchangeValue(
            @PathVariable String from,
            @PathVariable String to) {

        // Sleuth will assign ids to the specific request
        logger.info("retrieveExchangeValue called with {} to {}", from, to);

        CurrencyExchange currencyExchange = repository.findByFromAndTo(from, to);

        if (currencyExchange == null) {
            throw new RuntimeException("Unable to Find data for " + from + " to " + to);
        }

        // Get the port of this server
        String port = environment.getProperty("local.server.port");
        currencyExchange.setEnvironment(port);
        return currencyExchange;

    }

}
