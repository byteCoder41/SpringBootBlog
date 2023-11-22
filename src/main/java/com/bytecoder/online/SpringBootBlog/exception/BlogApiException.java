package com.bytecoder.online.SpringBootBlog.exception;

import org.springframework.http.HttpStatus;

public class BlogApiException extends RuntimeException {
    private HttpStatus s;
    private String msg;

    public BlogApiException(HttpStatus s, String msg) {
        this.s = s;
        this.msg = msg;
    }

    public HttpStatus getS() {
        return s;
    }

    public String getMsg() {
        return msg;
    }
}
