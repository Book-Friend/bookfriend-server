package com.book.service.user.dto.request;

import com.book.domain.user.User;
import lombok.*;

import javax.validation.constraints.Email;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCreateDto {

    private String name;

    @Email
    private String email;

    private String password;

    public User toEntity(){
        return User.builder()
                .email(this.email)
                .name(this.name)
                .password(this.password)
                .hidden(false)
                .role("ROLE_USER")
                .build();
    }
}
