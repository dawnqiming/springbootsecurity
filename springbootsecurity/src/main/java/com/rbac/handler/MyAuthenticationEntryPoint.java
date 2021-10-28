package com.rbac.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rbac.entity.RespResult;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 未认证（未登录）统一处理
 * @Author ChengJianSheng
 * @Date 2021/5/7
 */
@Component
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.write(objectMapper.writeValueAsString(new RespResult<>(0, "未登录，请先登录", null)));
        writer.flush();
        writer.close();
    }
}
