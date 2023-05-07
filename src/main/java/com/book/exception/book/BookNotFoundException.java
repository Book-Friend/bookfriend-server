package com.book.exception.book;

import com.book.exception.CustomRuntimeException;

public class BookNotFoundException extends CustomRuntimeException {

    public BookNotFoundException(String message){
        super(message);
        super.setName("BookNotFoundException");
    }
}
