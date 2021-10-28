package com.rbac.config;

import com.alibaba.fastjson.JSON;
import com.rbac.entity.UserEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @Description 前后端约定登录成功以后，将token放到header中。于是，我们需要过滤器来处理请求Header中的token，为此定义一个TokenFilter
 * @Author T480
 * @Version
 * @Date 2021/10/20
 */
@Component
public class TokenFilter extends OncePerRequestFilter {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String token = request.getHeader("token");
        System.out.println("请求头中带的token: " + token);
        String key = "TOKEN:" + token;
        if (StringUtils.isNotBlank(token)) {
            String value = stringRedisTemplate.opsForValue().get(key);
            if (StringUtils.isNotBlank(value)) {
//                String username = JwtUtils.extractUsername(token);
                UserEntity user = JSON.parseObject(value, UserEntity.class);
                if (null != user && null == SecurityContextHolder.getContext().getAuthentication()) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                    //  刷新token
                    //  如果生存时间小于10分钟，则再续1小时
                    long time = stringRedisTemplate.getExpire(key);
                    if (time < 600) {
                        stringRedisTemplate.expire(key, (time + 3600), TimeUnit.SECONDS);
                    }
                }
            }
        }

        chain.doFilter(request, response);
    }
}
