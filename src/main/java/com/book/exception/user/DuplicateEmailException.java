package com.book.exception.user;

import com.book.exception.CustomRuntimeException;

public class DuplicateEmailException extends CustomRuntimeException {

    public DuplicateEmailException(String message){
        super(message);
        super.setName("DuplicateEmailException");
    }
}
