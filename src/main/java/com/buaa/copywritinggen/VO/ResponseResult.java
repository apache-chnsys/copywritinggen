package com.buaa.copywritinggen.VO;

import com.buaa.copywritinggen.selfEnum.ResultCode;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


/**
 * 事前分流实验请求对象
 * @author sunzhen10
 * @date 2022/7/21 15:45
 */
public class ResponseResult<T> {
    private int code;

    private String msg;

    private T result;

    public ResponseResult() {
        this.setCode(ResultCode.SUCCESS.getCode());
        this.setMsg(ResultCode.SUCCESS.getMsg());
    }

    public ResponseResult(int successFlag, String msg) {
        this.setCode(successFlag);
        this.setMsg(msg);
    }

    public ResponseResult(ResultCode resultEnum, String msg) {
        this.setCode(resultEnum.getCode());
        this.setMsg(msg);
    }

    public ResponseResult(T result) {
        this.setCode(ResultCode.SUCCESS.getCode());
        this.setMsg(ResultCode.SUCCESS.getMsg());
        this.setResult(result);
    }

    public ResponseResult(String message, T result) {
        this.setCode(ResultCode.SUCCESS.getCode());
        this.setMsg(message);
        this.setResult(result);
    }

    public static <T> ResponseResult<T> success() {
        return new ResponseResult();
    }

    public static <T> ResponseResult<T> success(T result) {
        return new ResponseResult(result);
    }

    public static <T> ResponseResult<T> success(String message, T result) {
        return new ResponseResult(message, result);
    }

    public static <T> ResponseResult<T> error(String message) {
        return new ResponseResult(ResultCode.ERROR, message);
    }

    public static <T> ResponseResult<T> error(String message, T result) {
        ResponseResult<T> response = new ResponseResult(ResultCode.ERROR, message);
        response.setResult(result);
        return response;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getResult() {
        return this.result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
