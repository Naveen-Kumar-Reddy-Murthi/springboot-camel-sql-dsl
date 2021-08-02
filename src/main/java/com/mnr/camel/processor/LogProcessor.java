package com.mnr.camel.processor;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LogProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        log.info("output from the call is" + exchange.getIn().getBody(String.class));
    }
}
