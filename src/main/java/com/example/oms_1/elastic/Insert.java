package com.example.oms_1.elastic;

import co.elastic.clients.elasticsearch.core.IndexRequest;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.json.JsonData;
import com.example.oms_1.kafka.MessageService;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import quickfix.FieldNotFound;
import quickfix.field.OrderID;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import quickfix.*;

@Component
public class Insert {
    @Autowired
    private MessageService messageService;
    public void insertDataFromKafka(Message message) throws Exception {
        JsonObject jsonObject =  messageService.messageToJson(message);
        Reader input = new StringReader(String.valueOf(jsonObject));
        IndexRequest<JsonData> request = IndexRequest.of(i -> {
                    try {
                        return i
                                .index("order")
                                .id(String.valueOf(message.getField(new OrderID()).getValue()))
                                .withJson(input);
                    } catch (FieldNotFound fieldNotFound) {
                        fieldNotFound.printStackTrace();
                    }
                    return null;
                }
        );

        IndexResponse response = ConnectElastic.client.index(request);
        System.out.println("Indexed order with id " + response.id());
    }
}
