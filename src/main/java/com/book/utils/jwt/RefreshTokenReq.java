package com.book.utils.jwt;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RefreshTokenReq {

    private String refreshToken;
}
