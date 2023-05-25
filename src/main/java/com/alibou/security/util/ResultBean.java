package com.alibou.security.util;

import com.alibou.security.exception.ApplicationExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author mqz
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResultBean<T> {

    T data;

    String code;

    String message;

    public static <T> ResultBean<T> success(T data) {
        return ResultBean.<T>builder()
                .data(data)
                .code("200")
                .message("success")
                .build();
    }

    public static <T> ResultBean<T> success() {
        return ResultBean.<T>builder()
                .code("200")
                .message("success")
                .build();
    }

    public static <T> ResultBean<T> failure(String code, String message) {
        return ResultBean.<T>builder().code(code).message(message).build();
    }

    public static ResultBean<?> failure(ApplicationExceptionEnum exceptionEnum) {
        return ResultBean.failure(exceptionEnum.getCode(), exceptionEnum.getError());
    }

}
