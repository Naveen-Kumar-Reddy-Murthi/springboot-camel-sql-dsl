package com.mnr.camel.routes;

import com.mnr.camel.entity.Order;
import com.mnr.camel.processor.CreateRestRequestProcessor;
import com.mnr.camel.processor.LogProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RestRouteBuilder extends RouteBuilder {

    private JacksonDataFormat jacksonDataFormat = new JacksonDataFormat(Order.class);

    @Override
    public void configure() throws Exception {


        from("file:C:/inputGet?noop=true").setHeader(Exchange.HTTP_METHOD, simple("GET"))
                .to("http://localhost:3000/orders/89").process(new LogProcessor()).end();

        from("file:C:/inputPost?noop=true").process(new CreateRestRequestProcessor())
                .marshal(jacksonDataFormat)
                .setHeader(Exchange.HTTP_METHOD, simple("POST"))
                .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
                .to("http://localhost:3000/orders").process(new LogProcessor()).end();

        rest().post("/postOrder").consumes(MediaType.APPLICATION_JSON_VALUE)
                .bindingMode(RestBindingMode.json)
                .produces(MediaType.APPLICATION_JSON_VALUE)
                .type(Order.class)
                .outType(Order.class)
                .to("direct:order");

        /*from("direct:order")
                .log("Incoming Body is ${body}")
                .log("Incoming Body after unmarshal is ${body}")
                 .bean(this,"transformMessage")
                  .log("Outgoing pojo Body is ${body}")
                .process(new LogProcessor())
                .marshal(jacksonDataFormat)
                .process(new LogProcessor())
                .setHeader(Exchange.HTTP_METHOD, simple("POST"))
                .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
                .setHeader("Accept", constant("application/json"))
                .process(new LogProcessor())
                .log("Body after transformation is ${body} with headers: ${headers}")
                .process(new LogProcessor())
                .to("http://localhost:3000/orders?bridgeEndpoint=true&throwExceptionOnFailure=false")//.process(new LogProcessor())
                //.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200))
                .log("outgoing response body = ${body}")
//                .transform()
                //.marshal(jacksonDataFormat)
                //.simple("${body}")
//                .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
                .unmarshal().json(JsonLibrary.Jackson);*/

        from("direct:order")
                .marshal().json(JsonLibrary.Jackson)
                .setHeader("Content-Type", constant("application/json"))
                .setHeader("Accept", constant("application/json"))
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .removeHeader(Exchange.HTTP_PATH)
                .recipientList(simple("http://localhost:3000/orders?bridgeEndpoint=true&throwExceptionOnFailure=false"))
                .unmarshal().json(JsonLibrary.Jackson);
    }

    public void transformMessage(Exchange exchange) {
        Message in = exchange.getIn();
        Order gho = in.getBody(Order.class);
       /* List oi = gho.getOrderItems();
        List list = new ArrayList();
        for(GrubHubOrderItem i : oi){
            LineItem li = new LineItem(Item.valueOf(i.getOrderItem()),i.getName());
            list.add(li);
        }
        CreateOrderCommand coc = new CreateOrderCommand(list, null);
        */

        in.setBody(gho);
    }
}
