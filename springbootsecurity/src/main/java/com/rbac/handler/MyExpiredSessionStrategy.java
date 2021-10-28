package com.rbac.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rbac.entity.RespResult;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
/*
  * @Author T480
  * @Description
  * @Date 0:12 
  * @Param 
  * @return 
  **/
@Component
public class MyExpiredSessionStrategy implements SessionInformationExpiredStrategy {

    private static ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
        String msg = "登录超时或已在另一台机器登录，您被迫下线！";
        RespResult respResult = new RespResult(0, msg, null);
        HttpServletResponse response = event.getResponse();
        response.setContentType("application/json;charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.write(objectMapper.writeValueAsString(respResult));
        writer.flush();
        writer.close();
    }
}
