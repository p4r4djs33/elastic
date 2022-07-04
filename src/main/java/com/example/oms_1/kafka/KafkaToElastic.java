package com.example.oms_1.kafka;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class KafkaToElastic extends RouteBuilder {

    @Autowired
    MessageProcessor messageProcessor;

    static String TOPIC="topic-demo";



    @Override
    public void configure() throws Exception {
        from("kafka:" + TOPIC + "?brokers=192.168.1.74:9092" +
                "&groupId=group-2" +
                "&autoOffsetReset=latest")
                .process(messageProcessor);
    }
}
