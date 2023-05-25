package com.alibou.security.exception.enums;

import com.alibou.security.exception.ApplicationExceptionEnum;

/**
 * @author mqz
 */
public enum BaseErrorCode implements ApplicationExceptionEnum {


    UNKNOWN_ERROR("255", "服务器内部错误"),

    PARAMETER_ERROR("256","参数校验错误"),
    ;


    @Override
    public String getCode() {
        return CODE;
    }

    @Override
    public String getError() {
        return ERROR;
    }


    private final String CODE;
    private final String ERROR;

    BaseErrorCode(String code, String error) {
        this.CODE = code;
        this.ERROR = error;
    }
}
