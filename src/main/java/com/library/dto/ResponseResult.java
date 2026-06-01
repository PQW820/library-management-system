package com.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 统一返回结果类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseResult<T> {
    private Integer code;  // 响应码
    private String message;  // 响应信息
    private T data;  // 响应数据

    public ResponseResult(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static <T> ResponseResult<T> success(T data) {
        return new ResponseResult<>(200, "操作成功", data);
    }

    public static <T> ResponseResult<T> success(String message, T data) {
        return new ResponseResult<>(200, message, data);
    }

    public static <T> ResponseResult<T> success() {
        return new ResponseResult<>(200, "操作成功");
    }

    public static <T> ResponseResult<T> error(String message) {
        return new ResponseResult<>(500, message);
    }

    public static <T> ResponseResult<T> error(Integer code, String message) {
        return new ResponseResult<>(code, message);
    }
}