package com.alibou.security.exception.enums;

import com.alibou.security.exception.ApplicationExceptionEnum;

/**
 * @author mqz
 */
public enum AccountErrorCode implements ApplicationExceptionEnum {


    USERNAME_OR_PASSWORD_ERROR("-200", "用户名或密码错误"),
    INSUFFICIENT_PERMISSIONS("-201", "权限不足")
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

    AccountErrorCode(String code, String error) {
        CODE = code;
        ERROR = error;
    }

}
