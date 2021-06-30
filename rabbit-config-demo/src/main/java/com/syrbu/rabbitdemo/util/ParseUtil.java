package com.syrbu.rabbitdemo.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ParseUtil {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    public String parseObject(Object object) {
        return objectMapper.writeValueAsString(object);
    }
}
