package com.alibou.security.demo;

import com.alibou.security.exception.ApplicationException;
import com.alibou.security.util.ResultBean;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.alibou.security.exception.enums.AccountErrorCode.USERNAME_OR_PASSWORD_ERROR;

@RestController
@RequestMapping("/api/v1/demo-controller")
public class DemoController {

  @GetMapping
  @PreAuthorize("hasAnyAuthority('ADMIN')")
  public ResponseEntity<String> sayHello() {
    return ResponseEntity.ok("Hello from secured endpoint");
  }

  @PreAuthorize("hasAnyAuthority('USER')")
  @GetMapping("/hello")
  public ResultBean<String> hello() {
    return ResultBean.success();
  }

  @PostMapping("/test-validation")
  public ResultBean<?> testValidation(
          @RequestBody @Validated RequestOfValid req
  ) {
    if (!req.x().equals("0")) throw new ApplicationException(USERNAME_OR_PASSWORD_ERROR);
    return ResultBean.success();
  }
}


record RequestOfValid(
        @NotBlank(message = "x不能为空") String x,
        @NotNull(message = "y不能为空") Integer y
) {}