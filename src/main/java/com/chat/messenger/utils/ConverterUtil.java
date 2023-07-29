package com.chat.messenger.utils;

import com.google.gson.Gson;

public class ConverterUtil {
    public static String toJson(Object data) {
        return new Gson().toJson(data);
    }
}
