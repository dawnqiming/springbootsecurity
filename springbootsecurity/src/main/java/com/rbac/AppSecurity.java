package com.rbac;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @ClassName AppSecurity
 * @Author **
 * @Version V1.0
 **/
@MapperScan("com.rbac.mapper")
@SpringBootApplication
public class AppSecurity {
    public static void main(String[] args) {
        SpringApplication.run(AppSecurity.class);
    }
}
