package com.camel.process;

import com.camel.service.CityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class CityProcessor implements Processor {
    final CityService cityService;

    @Transactional
    public void process(Exchange exchange) throws Exception {
        cityService.saveCity("Ziguinchor 8");
    }
}
