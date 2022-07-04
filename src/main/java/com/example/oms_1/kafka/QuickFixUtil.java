package com.example.oms_1.kafka;

import quickfix.ConfigError;
import quickfix.DataDictionary;
import quickfix.DefaultMessageFactory;
import quickfix.MessageFactory;
import quickfix.field.ApplVerID;

public class QuickFixUtil {
    public static DataDictionary DATA_DICTIONARY;

    static {
        try {
            DATA_DICTIONARY = new DataDictionary("C:\\Windows-D\\08-FSS\\FIX44.xml");
        } catch (ConfigError configError) {
            configError.printStackTrace();
        }
    }

    public static MessageFactory MESSAGE_FACTORY = new DefaultMessageFactory(ApplVerID.FIX44);

    public QuickFixUtil() throws ConfigError {
    }
}
