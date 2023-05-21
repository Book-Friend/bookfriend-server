package com.book.service.user.dto.response;

import com.book.domain.user.User;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserSearchDto {

    private Long id;

    private String name;

    private String photo;

    public static UserSearchDto from(User user){
        return UserSearchDto.builder()
                .id(user.getId())
                .name(user.getName())
                .photo(user.getImage())
                .build();
    }
}
