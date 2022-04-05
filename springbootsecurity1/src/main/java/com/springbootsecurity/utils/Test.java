package com.springbootsecurity.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @Description
 * @Author T480
 * @Version
 * @Date 2022040201
 */
public class Test {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        for (int i = 0; i < 10; i++) {
            System.out.println(encoder.encode("123"));
        }
    }
}
