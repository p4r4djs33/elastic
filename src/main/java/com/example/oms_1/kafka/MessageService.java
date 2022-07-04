package com.example.oms_1.kafka;

import com.google.gson.JsonObject;
import org.springframework.stereotype.Service;
import quickfix.*;

import java.util.Iterator;

@Service
public class MessageService {

    public JsonObject messageToJson(Message message) throws Exception {
        JsonObject jsonObject = new JsonObject();
        Iterator<Field<?>> iterator = message.iterator();
        while (iterator.hasNext()) {
            Field<?> field = iterator.next();
            jsonObject.addProperty(QuickFixUtil.DATA_DICTIONARY.getFieldName(field.getField()) != null ? QuickFixUtil.DATA_DICTIONARY.getFieldName(field.getField()) : "" + field.getTag() + "", (String) (field.getObject() != null ? field.getObject() : ""));
        }
        Iterator<Integer> iteratorKeys = message.groupKeyIterator();
        int key;
        while (iteratorKeys.hasNext()) {
            key = iteratorKeys.next();
            for (Group group : message.getGroups(key)) {
                iterator = group.iterator();
                jsonObject = new JsonObject();
                while (iterator.hasNext()) {
                    Field<?> field = iterator.next();
                    jsonObject.addProperty(QuickFixUtil.DATA_DICTIONARY.getFieldName(field.getField()) != null ? QuickFixUtil.DATA_DICTIONARY.getFieldName(field.getField()) : "" + field.getTag() + "", (String) (field.getObject() != null ? field.getObject() : ""));
                }
            }
        }
        return jsonObject;
    }
}
