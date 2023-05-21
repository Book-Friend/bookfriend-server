package com.book.service.user.dto.request;

import lombok.*;

import javax.validation.constraints.Email;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginDto {

    @Email
    private String username;

    private String password;

}
