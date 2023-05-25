package com.alibou.security.exception;

/**
 * @author mqz
 */
public class ApplicationException extends RuntimeException{

    private final String CODE;

    private final String ERROR;


    public ApplicationException(String code, String error) {
        super(error);
        this.CODE = code;
        this.ERROR = error;
    }

    public ApplicationException(ApplicationExceptionEnum exceptionEnum) {
        super(exceptionEnum.getError());
        this.ERROR = exceptionEnum.getError();
        this.CODE = exceptionEnum.getCode();
    }

    public String getCode() {
        return CODE;
    }

    public String getError() {
        return ERROR;
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}
