package com.book.exception.book;

import com.book.exception.CustomRuntimeException;

public class DuplicateBookException extends CustomRuntimeException {

    public DuplicateBookException(String msg) {
        super(msg);
        this.setName("DuplicateBookException");
    }
}
