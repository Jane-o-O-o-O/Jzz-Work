package com.jzz.model;

import java.io.Serializable;

/**
 * 统一API响应结果封装类
 * Unified API Response Result Wrapper Class
 *
 * 用于封装所有 AJAX 请求的返回结果
 * Used to encapsulate all AJAX request responses
 *
 * @author Jzz
 * @version 1.0
 * @param <T> 数据类型 (Data Type)
 */
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 响应状态码
     * Response Status Code
     * 200: 成功 (Success)
     * 400: 客户端错误 (Client Error)
     * 500: 服务器错误 (Server Error)
     */
    private Integer code;

    /**
     * 响应消息
     * Response Message
     */
    private String message;

    /**
     * 响应数据
     * Response Data
     */
    private T data;

    // ==================== 构造方法 (Constructors) ====================

    /**
     * 无参构造方法
     * No-argument Constructor
     */
    public Result() {
    }

    /**
     * 全参构造方法
     * Full-argument Constructor
     */
    public Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    // ==================== 静态工厂方法 (Static Factory Methods) ====================

    /**
     * 创建成功响应（无数据）
     * Create success response (without data)
     *
     * @return Result 对象
     */
    public static <T> Result<T> success() {
        return new Result<>(200, "操作成功", null);
    }

    /**
     * 创建成功响应（带数据）
     * Create success response (with data)
     *
     * @param data 响应数据
     * @return Result 对象
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(200, "操作成功", data);
    }

    /**
     * 创建成功响应（带消息和数据）
     * Create success response (with message and data)
     *
     * @param message 响应消息
     * @param data 响应数据
     * @return Result 对象
     */
    public static <T> Result<T> success(String message, T data) {
        return new Result<>(200, message, data);
    }

    /**
     * 创建失败响应
     * Create failure response
     *
     * @param message 错误消息
     * @return Result 对象
     */
    public static <T> Result<T> error(String message) {
        return new Result<>(500, message, null);
    }

    /**
     * 创建失败响应（带状态码）
     * Create failure response (with status code)
     *
     * @param code 状态码
     * @param message 错误消息
     * @return Result 对象
     */
    public static <T> Result<T> error(Integer code, String message) {
        return new Result<>(code, message, null);
    }

    /**
     * 创建客户端错误响应（400）
     * Create client error response (400)
     *
     * @param message 错误消息
     * @return Result 对象
     */
    public static <T> Result<T> badRequest(String message) {
        return new Result<>(400, message, null);
    }

    // ==================== Getter 和 Setter 方法 (Getter and Setter Methods) ====================

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    /**
     * 判断是否成功
     * Check if successful
     *
     * @return true 成功，false 失败
     */
    public boolean isSuccess() {
        return code != null && code == 200;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
