package com.book.exception;

import lombok.Getter;
import lombok.Setter;

public class CustomRuntimeException extends RuntimeException{

    @Getter @Setter
    private String name;

    public CustomRuntimeException(String msg){
        super(msg);
    }
}
