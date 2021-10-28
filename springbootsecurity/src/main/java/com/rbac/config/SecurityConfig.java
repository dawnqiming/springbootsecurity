package com.rbac.config;

import com.rbac.entity.PermissionEntity;
import com.rbac.handler.MyAuthenticationFailureHandler;
import com.rbac.handler.MyAuthenticationSuccessHandler;
import com.rbac.handler.MyLogoutSuccessHandler;
import com.rbac.mapper.PermissionMapper;
import com.rbac.service.MemberUserDetailsService;
import com.rbac.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName SecurityConfig
 * @Author 蚂蚁课堂余胜军 QQ644064779 www.mayikt.com
 * @Version V1.0
 **/
@Component
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private MemberUserDetailsService memberUserDetailsService;
    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;
    @Autowired
    private MyAuthenticationFailureHandler myAuthenticationFailureHandler;
    @Autowired
    private TokenFilter tokenFilter;

    /**
     * 添加授权账户
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        // 设置用户账号信息和权限
//        auth.inMemoryAuthentication().withUser("mayikt_admin").password("mayikt")
//                .authorities("addMember", "delMember", "updateMember", "showMember");
//        // 如果mayikt_admin账户权限的情况 所有的接口都可以访问，如果mayikt_add 只能访问addMember
//        auth.inMemoryAuthentication().withUser("mayikt_add").password("mayikt")
//                .authorities("addMember");
        auth.userDetailsService(memberUserDetailsService).passwordEncoder(new PasswordEncoder() {
            /**
             * 对密码MD5
             * @param rawPassword
             * @return
             */
            @Override
            public String encode(CharSequence rawPassword) {
                return MD5Util.encode((String) rawPassword);
            }

            /**
             * rawPassword 用户输入的密码
             * encodedPassword 数据库DB的密码
             * @param rawPassword
             * @param encodedPassword
             * @return
             */
            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                String rawPass = MD5Util.encode((String) rawPassword);
                boolean result = rawPass.equals(encodedPassword);
                return result;
            }
        });
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        //配置httpBasic Http协议认证
////        http.authorizeRequests().antMatchers("/**").fullyAuthenticated().and().httpBasic();
//        //配置httpBasic Http协议认证
//        http.authorizeRequests().antMatchers("/**").fullyAuthenticated().and().formLogin();

//        // 默认fromLogin
        List<PermissionEntity> allPermission = permissionMapper.findAllPermission();
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry
                expressionInterceptUrlRegistry = http.authorizeRequests();
        allPermission.forEach((permission) -> {
            expressionInterceptUrlRegistry.antMatchers(permission.getUrl()).
                    hasAnyAuthority(permission.getPermTag());

        });
        expressionInterceptUrlRegistry.antMatchers("/login").permitAll()
                .antMatchers("/**").fullyAuthenticated()
                .and()
                .formLogin().successHandler(myAuthenticationSuccessHandler)
                .failureHandler(myAuthenticationFailureHandler)
                .and()
                .logout().logoutSuccessHandler(new MyLogoutSuccessHandler());
//                .and().formLogin().loginPage("/login").and().csrf().disable();
        //.antMatchers("/addMember") 配置addMember请求权限 hasAnyAuthority("addMember")

    }

    /**
     * There is no PasswordEncoder mapped for the id "null"
     * 原因:升级为Security5.0以上密码支持多中加密方式，恢复以前模式
     *
     * @return
     */
    @Bean
    public static NoOpPasswordEncoder passwordEncoder() {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }

}
