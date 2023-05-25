package com.alibou.security.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author mqz
 */
@Component
public class ResponseUtil {

    private static ObjectMapper objectMapper;

    public ResponseUtil(ObjectMapper objectMapper) {
        ResponseUtil.objectMapper = objectMapper;
    }


    public static void responseJson(HttpServletResponse response, ResultBean<?> result) {
        response.setStatus(HttpStatus.OK.value());
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        try {
            response.getWriter().write(
                    objectMapper.writeValueAsString(result)
            );
        } catch (IOException e) {
            throw new RuntimeException("序列化JSON异常" + e.getMessage(), e);
        }
    }

}


