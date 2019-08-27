package com.yc.yclibx.exception;

/**
 * 基础异常类
 */

public class YcException extends Exception {
    private int code = -1;

    public YcException() {
        this("");
    }

    public YcException(String msg) {
        super(msg);
    }

    public YcException(String msg, int code) {
        super(msg);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
