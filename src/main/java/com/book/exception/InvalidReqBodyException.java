package com.book.exception;

public class InvalidReqBodyException extends CustomRuntimeException{

    public InvalidReqBodyException(String msg) {
        super(msg);
        this.setName("InvalidReqBodyException");
    }
}
