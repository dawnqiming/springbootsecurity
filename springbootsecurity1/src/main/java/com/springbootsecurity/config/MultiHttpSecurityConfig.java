//package com.springbootsecurity.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.annotation.Order;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.password.NoOpPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
///**
// * 配置多个httpSecurity 不需要继承WebSecurityConfigurerAdapter，只需要实现一个方法
// * @Author T480
// * @Version
// * @Date 2022040123
// */
//@Component
//public class MultiHttpSecurityConfig {
//
//    @Bean
//    PasswordEncoder passwordEncoder() {
//        return NoOpPasswordEncoder.getInstance();
//    }
//
//    @Autowired
//    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication() // 在内存中配置
//                .withUser("hard").password("111").roles("hard")
//                .and()
//                .withUser("admin").password("222").roles("admin")
//                .and()
//                .withUser("user").password("333").roles("user");
//    }
//
//    @Configuration
//    // 数值越小优先级越高
//    @Order(1)
//    public static class AdminSecurityConfig extends WebSecurityConfigurerAdapter {
//        @Override
//        protected void configure(HttpSecurity http) throws Exception {
//            http.antMatcher("/admin/**").authorizeRequests()
//                    .anyRequest().hasRole("admin");
//        }
//    }
//
//    @Configuration
//    public static class OtherSecurityConfig extends WebSecurityConfigurerAdapter {
//        @Override
//        protected void configure(HttpSecurity http) throws Exception {
//            http.authorizeRequests()
//                    .anyRequest().authenticated()
//                    .and()
//                    .formLogin()
//                    .loginProcessingUrl("/login")
//                    .permitAll()
//                    .and()
//                    .csrf()
//                    .disable();
//        }
//    }
//}
