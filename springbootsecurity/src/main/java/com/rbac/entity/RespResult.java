package com.rbac.entity;

import lombok.Getter;

/**
 * @Description
 * @Author T480
 * @Version
 * @Date 2021/10/20
 */
@Getter
public class RespResult<T> {
    private T status;
    private String message;
    private String token;

    public RespResult(T status, String message, String token) {
        this.status = status;
        this.message = message;
        this.token = token;
    }
}