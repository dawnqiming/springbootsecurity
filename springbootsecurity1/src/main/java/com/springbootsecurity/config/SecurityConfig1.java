package com.springbootsecurity.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @Author T480
 * @Version
 * @Date 2022033023
 */
@Configuration
public class SecurityConfig1 extends WebSecurityConfigurerAdapter {

    @Bean
    PasswordEncoder passwordEncoder() {
        //return NoOpPasswordEncoder.getInstance();  // 密码不用加密设置
        return new BCryptPasswordEncoder();
    }

    /**
     * 角色继承 ROLE_admin 具有 ROLE_user的权限
     *
     * 配置多个时1.0是空格区别，2.0使用\n
     * @return org.springframework.security.access.hierarchicalroles.RoleHierarchy
     **/
    @Bean
    RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();
        hierarchy.setHierarchy("ROLE_root > ROLE_admin \n ROLE_admin > ROLE_user");
        return hierarchy;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/js/**", "/css/**", "/images/**");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication() // 在内存中配置
                .withUser("root").password("$2a$10$YSCFfkuvSZQN5BoPGnPemuOqHb9V25oGOSxP6DMminloBrxUp0bim").roles("root")
                .and()
                .withUser("admin").password("$2a$10$agMExaESpNUEddqPG9.hY.nUSEKj8biYeJVJn1VRYtr6OqjeUWoAu").roles("admin")
                .and()
                .withUser("user").password("$2a$10$8s6lgyo8syHTTfbS/8Gl4Or4osBJoA87ZMbLthUx6YJDfaEYK/O8a").roles("user");
    }

    /**
     * @return void
     * @Description 配置权限拦截
     * @Date 2022/3/31 0:56
     * @Param [http]
     **/
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests() //允许匹配，开启匹配
                .antMatchers("/root/**").hasRole("root")
                .antMatchers("/admin/**").hasRole("admin")
                .antMatchers("/user/**").hasAnyRole("user", "admin")
                .anyRequest().authenticated()  // 其他的任意的只要登录就能访问
                .and()
                .formLogin()
                .loginProcessingUrl("/doLogin") // 处理登录的接口
//                .loginPage("/login")   // 默认的登录页面，如果前后端分离不需要
//                .usernameParameter("username")  // 登录时的参数名
//                .passwordParameter("password") // 登录时的参数名
                .successHandler((req, resp, authentication) -> {
                    Object principal = authentication.getPrincipal();
                    resp.setContentType("application/json;charset=utf-8");
                    PrintWriter out = resp.getWriter();
                    out.write(new ObjectMapper().writeValueAsString(principal));
                    out.flush();
                    out.close();
                })
                .failureHandler(new AuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                        AuthenticationException exception) throws IOException, ServletException {
                        response.setContentType("application/json;charset=utf-8");
                        PrintWriter out = response.getWriter();
                        out.write(exception.getMessage());
                        out.flush();
                        out.close();
                    }
                })
                .permitAll()   //和登录相关的额接口直接访问
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler((req, resp, authentication) -> {
                    resp.setContentType("application/json;charset=utf-8");
                    PrintWriter out = resp.getWriter();

                    Map<String,Object> result = new HashMap<>();
                    result.put("code" , "200");
                    result.put("message", "注销成功");

                    out.write(new ObjectMapper().writeValueAsString(result));
                    out.flush();
                    out.close();
                })
                .and()
                .csrf().disable()
                ;
    }
}
