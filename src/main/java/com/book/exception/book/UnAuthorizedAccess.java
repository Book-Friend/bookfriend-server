package com.book.exception.book;

import com.book.exception.CustomRuntimeException;

public class UnAuthorizedAccess extends CustomRuntimeException {

    public UnAuthorizedAccess(String message){
        super(message);
        super.setName("UnAuthorizedAccess");
    }
}
