package com.book.exception.user;

import com.book.exception.CustomRuntimeException;

public class UserNotFoundException extends CustomRuntimeException {

    public UserNotFoundException(){
        super("존재하지 않는 사용자입니다.");
        super.setName("UserNotFoundException");
    }

    public UserNotFoundException(String message){
        super(message);
        super.setName("UserNotFoundException");
    }
}
