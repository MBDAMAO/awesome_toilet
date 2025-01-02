package com.damao.common.result;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result<E> implements Serializable {
    private Integer code;
    private String message;
    private E data;

    public static <E> Result<E> success() {
        Result<E> result = new Result<>();
        result.setCode(0);
        result.message = "";
        return result;
    }

    private void setCode(int i) {
        this.code = i;
    }

    public static <E> Result<E> success(E data) {
        Result<E> result = new Result<>();
        result.data = data;
        result.message = "";
        result.setCode(0);
        return result;
    }

    public static <E> Result<E> error(String msg) {
        Result<E> result = new Result<>();
        result.message = msg;
        result.setCode(1);
        return result;
    }

    public static <E> Result<E> success(E data, String msg) {
        Result<E> result = new Result<>();
        result.data = data;
        result.message = msg;
        result.setCode(0);
        return result;
    }
}
