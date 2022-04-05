package com.springbootsecurity.controller;

import com.springbootsecurity.service.Impl.MemberServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author T480
 * @Version
 * @Date 2022033021
 */
@RestController
public class HelloController {

    @Autowired
    private MemberServiceImpl memberService;

    @GetMapping("/hello")
    public String getIndex() {
        return "Hello Security";
    }

    @GetMapping("/admin/hello")
    public String getAdmin() {
        return "Hello admin";
    }

    @GetMapping("/user/hello")
    public String getUser() {
        return "Hello user";
    }

    @GetMapping("/hello1")
    public String admin() {
        return memberService.admin();
    }

    @GetMapping("/hello2")
    public String user() {
        return memberService.user();
    }

    @GetMapping("/hello3")
    public String hello() {
        return memberService.hello();
    }
}
