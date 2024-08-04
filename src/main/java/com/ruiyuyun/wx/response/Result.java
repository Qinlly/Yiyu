package com.ruiyuyun.wx.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    private int code;
    private String message;
    private Object data;

    public static Result success(Object data) {
        return new Result(200, "success", data);
    }
    public static Result success() {
        return new Result(200, "success",null);
    }

    public static Result error(String message) {
        return new Result(0, message, null);
    }

    public String toString() {
        return "Result [code=" + code + ", message=" + message + ", data=" + data + "]";
    }

    public String toJsonString() {
        return "{\"code\":" + code + ",\"message\":\"" + message + "\",\"data\":" + data + "}";
    }
}
