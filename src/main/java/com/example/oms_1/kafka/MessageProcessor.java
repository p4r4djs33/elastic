package com.example.oms_1.kafka;

import com.example.oms_1.elastic.Insert;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import quickfix.Message;
import quickfix.MessageUtils;

@Component
public class MessageProcessor implements Processor {

    @Autowired
    private Insert insertData;

    @Override
    public void process(Exchange exchange) throws Exception {
        String rawMessageString = exchange.getIn().getBody(String.class);

        Message message = (Message) MessageUtils.parse(QuickFixUtil.MESSAGE_FACTORY, QuickFixUtil.DATA_DICTIONARY, rawMessageString, false);

        insertData.insertDataFromKafka(message);


    }
}
