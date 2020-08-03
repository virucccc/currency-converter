package com.virucccc.converter.component;

import com.virucccc.converter.model.Valute;
import com.virucccc.converter.repository.ExchangeRepository;
import com.virucccc.converter.repository.ValuteRepository;
import com.virucccc.converter.utils.ValutesExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class StartupApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private ValuteRepository valuteRepository;

    @Autowired
    private ExchangeRepository exchangeRepository;


    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        List<Valute> valutes = valuteRepository.findAll();
        valutes = ValutesExchange.createValute(valuteRepository, valutes);
        ValutesExchange.createExchanges(exchangeRepository, valutes);
    }
}