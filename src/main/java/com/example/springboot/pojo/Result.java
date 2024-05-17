package com.example.springboot.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

// 响应结果
@Data
@NoArgsConstructor
public class Result<T> {
    private Integer code; // 状态码 0：成功 1：失败
    private String message; // 提示信息
    private T data; // 数据

    public Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <E> Result<E> success(E data) {
        return new Result<>(0, "success", data);
    }
    // 快速返回操作成功响应
    public static Result success(){
        return new Result(0, "success", null);
    }
    public static <E> Result<E> success(Integer code, E data){
        return new Result<>(code, "类别", data);
    }
    public static Result error(Integer code, String message){
        return new Result(code, message, null);
    }
}
