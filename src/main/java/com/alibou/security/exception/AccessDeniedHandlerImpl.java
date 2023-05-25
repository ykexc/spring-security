package com.alibou.security.exception;

import com.alibou.security.util.ResponseUtil;
import com.alibou.security.util.ResultBean;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import static com.alibou.security.exception.enums.AccountErrorCode.INSUFFICIENT_PERMISSIONS;

/**
 * @author mqz
 * 因为拦截顺序导致这没啥用了
 */
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    /**
     * Handles an access denied failure.
     *
     * @param request               that resulted in an <code>AccessDeniedException</code>
     * @param response              so that the user agent can be advised of the failure
     * @param accessDeniedException that caused the invocation
     */
    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException
    ) {
        ResponseUtil.responseJson(response, ResultBean.failure(INSUFFICIENT_PERMISSIONS));
    }
}
