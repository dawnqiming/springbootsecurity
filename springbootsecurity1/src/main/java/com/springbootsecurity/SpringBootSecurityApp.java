package com.springbootsecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

/**
 * @EnableWebSecurity  springboot项目，不需要此注解
 * @EnableGlobalMethodSecurity 开启方法安全校验，参数含义：第一个，开启后可以使用@PreAuthorize()-方法执行之前校验
 * 和@@PostAuthorize()-方法执行之后校验
 * 第二个securedEnabled 开启后可以使用表达式，使用@Secured("ROLE_user")注解
 * @Author T480
 * @Version
 * @Date 2022033020
 */
@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true)
public class SpringBootSecurityApp {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootSecurityApp.class,args);
    }

}
