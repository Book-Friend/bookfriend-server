package com.book.exception.user;

import com.book.exception.CustomRuntimeException;

public class PasswordException extends CustomRuntimeException {

    public PasswordException(String message){
        super(message);
        super.setName("PasswordException");
    }
}
