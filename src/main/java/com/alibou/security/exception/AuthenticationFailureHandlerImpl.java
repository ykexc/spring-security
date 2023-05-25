package com.alibou.security.exception;

import com.alibou.security.util.ResponseUtil;
import com.alibou.security.util.ResultBean;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

/**
 * @author mqz
 * 自定义了loginurl,这没啥用了
 */
public class AuthenticationFailureHandlerImpl implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception
    ) throws IOException, ServletException {
        ResponseUtil.responseJson(response, ResultBean.failure("-200", "用户名或密码错误"));
    }
}
