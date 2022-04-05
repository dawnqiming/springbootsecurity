package com.springbootsecurity.service.Impl;

import org.springframework.context.annotation.Role;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

/**
 * sercurity 方法安全
 * @Author T480
 * @Version
 * @Date 2022040300
 */
@Service
public class MemberServiceImpl {

    @PreAuthorize("hasRole('admin')")
    public String admin() {
        return "admin";
    }

    @Secured("ROLE_user")
    public String user() {
        return "user";
    }

    @PreAuthorize("hasAnyRole('admin', 'user')")
    public String hello() {
        return "hello";
    }


}
