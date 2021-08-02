package com.mnr.camel.processor;

import com.mnr.camel.OrderRepoistory;
import com.mnr.camel.entity.Order;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.BeanInject;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Data
@Slf4j
@Component
public class CreateRestRequestProcessor implements Processor {

    @Autowired
    private OrderRepoistory orderRepoistory;

    @Override
    public void process(Exchange exchange) throws Exception {
        log.info("Response body "+exchange.getIn().getBody(String.class));
        exchange.getIn().setBody(new Order(1, "Mac", 120000));
    }
}
