package com.book.speedTest;

import com.book.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class UserPerformance {

    @Autowired
    private UserService userService;

    @Test
    void findByEmail(){
        userService.getUserInfo("test@test.com_58385");
    }
}
