package com.book.exception.book;

import com.book.exception.CustomRuntimeException;

public class TagNotFoundException extends CustomRuntimeException {

    public TagNotFoundException(String message){
        super(message);
        super.setName("TagNotFoundException");
    }
}
