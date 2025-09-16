package com.riverstone.unknown303.errorlib.misc;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class GsonHelper {
    public static String toJsonString(JsonElement json) {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        return gson.toJson(json);
    }

    public static JsonElement fromJsonString(String json) {
        return JsonParser.parseString(json);
    }

    public static JsonElement copy(JsonElement data) {
        return data.deepCopy();
    }

    public static <T> JsonElement fromObject(T src) {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        String json = gson.toJson(src);
        return fromJsonString(json);
    }

    @SuppressWarnings("unchecked")
    public static <T> T toObject(JsonElement json, Class<? super T> type) {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        return (T) gson.fromJson(json, type);
    }
}
