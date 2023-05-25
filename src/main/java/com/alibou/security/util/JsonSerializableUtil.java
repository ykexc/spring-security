package com.alibou.security.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author mqz
 */
public class JsonSerializableUtil {

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static <T> String serializable(T t) {
        try {
            OBJECT_MAPPER.writeValueAsString(t);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static <R> R deserializable(String src, Class<? extends R> clazz) {
        try {
            return OBJECT_MAPPER.readValue(src, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
