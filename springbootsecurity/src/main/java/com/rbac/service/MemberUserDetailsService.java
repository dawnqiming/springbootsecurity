package com.rbac.service;

import com.rbac.entity.PermissionEntity;
import com.rbac.entity.UserEntity;
import com.rbac.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName MemberUserDetailsService
 * @Author ***
 * @Version V1.0
 **/
@Component
@Slf4j
public class MemberUserDetailsService implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;

    /**
     * loadUserByUserName
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1.根据该用户名称查询在数据库中是否存在
        UserEntity userEntity = userMapper.findByUsername(username);
        if (userEntity == null) {
            return null;
        }
        // 2.查询对应的用户权限
        List<PermissionEntity> listPermission = userMapper.findPermissionByUsername(username);
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        listPermission.forEach(permission -> {
            authorities.add(new SimpleGrantedAuthority(permission.getPermTag()));
        });
        log.info(">>>authorities:{}<<<", authorities);
        // 3.将该权限添加到security
        userEntity.setAuthorities(authorities);
        return userEntity;
    }

    public static void main(String[] args) {
//        UserDetailsService userDetailsService = (username) -> {
//            return null;
//        };
    }
}
