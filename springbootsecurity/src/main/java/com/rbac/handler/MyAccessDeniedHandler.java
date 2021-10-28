package com.rbac.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rbac.entity.RespResult;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/*
  * @Author T480
  * @Description  https://www.cnblogs.com/cjsblog/p/14904861.html
  * @Date 1:35 
  * @Param 
  * @return 
  **/
@Component("myAccessDecisionService")
public class MyAccessDeniedHandler implements AccessDeniedHandler {

    private static ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.write(objectMapper.writeValueAsString(new RespResult<>(0, "抱歉，您没有权限访问", null)));
        writer.flush();
        writer.close();
    }
}
