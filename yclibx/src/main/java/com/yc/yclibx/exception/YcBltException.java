package com.yc.yclibx.exception;

/**
 *
 */
public class YcBltException extends YcException {
    public YcBltException() {
    }

    public YcBltException(String msg) {
        super(msg);
    }
    public YcBltException(String msg, int code) {
        super(msg,code);
    }
}
