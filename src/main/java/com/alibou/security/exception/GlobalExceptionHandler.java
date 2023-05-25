package com.alibou.security.exception;

import com.alibou.security.exception.enums.BaseErrorCode;
import com.alibou.security.util.ResultBean;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Set;
import java.util.StringJoiner;

import static com.alibou.security.exception.enums.AccountErrorCode.INSUFFICIENT_PERMISSIONS;

/**
 * @author mqz
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResultBean<?> unknownError(Exception e) {
        log.error(e.getMessage());
        //we can send a message to the administrator to inform the system of any error
        return ResultBean.failure(BaseErrorCode.UNKNOWN_ERROR);
    }


    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    public ResultBean<?> accessDeniedHandler(AccessDeniedException e) {
        return ResultBean.failure(INSUFFICIENT_PERMISSIONS);
    }


    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public ResultBean<?> handleMethodArgumentNotValidException(
            ConstraintViolationException e
    ) {
        Set<ConstraintViolation<?>>
                constraintViolations = e.getConstraintViolations();
        var stringJoiner =
                new StringJoiner(",");
        for (var constraintViolation : constraintViolations) {
            stringJoiner.add(constraintViolation.getMessage());
        }
        return ResultBean.failure(BaseErrorCode.PARAMETER_ERROR.getCode(), stringJoiner.toString());
    }

    @ExceptionHandler(ApplicationException.class)
    @ResponseBody
    public ResultBean<?> applicationException(ApplicationException e) {
        return ResultBean.failure(e.getCode(), e.getError());
    }

    @ExceptionHandler(BindException.class)
    @ResponseBody
    public ResultBean<?> bindException(BindException e) {
        List<FieldError> fieldErrors = e.getFieldErrors();
        var sj = new StringJoiner(",");
        for (var field : fieldErrors) {
            sj.add(field.getDefaultMessage());
        }
        return ResultBean.failure(BaseErrorCode.PARAMETER_ERROR.getCode(), sj.toString());
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResultBean<?> methodArgumentNotValidException(
            MethodArgumentNotValidException e
    ) {
        var sj = new StringJoiner(",");
        List<FieldError> fieldErrors = e.getFieldErrors();
        for (var field : fieldErrors) {
            sj.add(field.getDefaultMessage());
        }
        return ResultBean.failure(BaseErrorCode.PARAMETER_ERROR.getCode(), sj.toString());
    }


}
