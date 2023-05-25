package com.alibou.security.auth;

import com.alibou.security.config.JwtService;
import com.alibou.security.dao.User;
import com.alibou.security.dao.mapper.UserMapper;
import com.alibou.security.user.Role;
import com.alibou.security.util.ResponseUtil;
import com.alibou.security.util.ResultBean;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final StringRedisTemplate stringRedisTemplate;

    public ResultBean<AuthenticationResponse> register(RegisterRequest request) {
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER.name())
                .build();
        userMapper.insert(user);
        var jwtToken = jwtService.generateToken(
                com.alibou.security.user.User.builder().firstname(request.getFirstname())
                        .lastname(request.getLastname())
                        .email(request.getEmail())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .role(Role.USER)
                        .build()
        );
        var refreshToken = jwtService.generateRefreshToken();
        stringRedisTemplate.opsForValue()
                .set(user.getEmail() + ":refresh", refreshToken, Duration.ofDays(7L));
        return ResultBean.success(AuthenticationResponse.builder()
                .token(jwtToken)
                .refreshToken(refreshToken)
                .build());
    }

    public ResultBean<AuthenticationResponse> authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userMapper.selectOne(request.getEmail());

        var userOfDetail =
                com.alibou.security.user.User.builder().firstname(user.getFirstname())
                        .lastname(user.getLastname())
                        .email(user.getEmail())
                        .password(user.getPassword())
                        .role("USER".equals(user.getRole()) ? Role.USER : Role.ADMIN).build();

        var jwtToken = jwtService.generateToken(userOfDetail);
        var refreshToken = jwtService.generateRefreshToken();
        stringRedisTemplate.opsForValue()
                .set(user.getEmail() + ":refresh", refreshToken, Duration.ofDays(7L));
        AuthenticationResponse response = AuthenticationResponse.builder()
                .token(jwtToken)
                .refreshToken(refreshToken)
                .build();
        return ResultBean.success(response);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String refreshToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String email = request.getHeader("userEmail");
        if (!StringUtils.hasText(refreshToken) || !StringUtils.hasText(email)) return;
        String key = email + ":refresh";
        String value = stringRedisTemplate.opsForValue().get(key);
        if (StringUtils.hasText(value) && value.equals(refreshToken)) {
            stringRedisTemplate.opsForValue().set(key, refreshToken, Duration.ofDays(7L));
        }
        var user = userMapper.selectOne(email);
        var userOfDetail =
                com.alibou.security.user.User.builder().firstname(user.getFirstname())
                        .lastname(user.getLastname())
                        .email(user.getEmail())
                        .password(user.getPassword())
                        .role("USER".equals(user.getRole()) ? Role.USER : Role.ADMIN).build();
        ResponseUtil.responseJson(
                response,
                ResultBean.success(new AuthenticationResponseOfRefreshToken(jwtService.generateToken(userOfDetail)))
        );
    }
}
