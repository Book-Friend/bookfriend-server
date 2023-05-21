package com.book.service;

import com.book.domain.user.User;
import com.book.service.user.dto.request.UserCreateDto;
import com.book.exception.user.DuplicateEmailException;
import com.book.exception.user.UserNotFoundException;
import com.book.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void signUp() {
        UserCreateDto createDto = new UserCreateDto("kim", "kim@kim.com", "1234");
        User user = userService.signUp(createDto);

        assertEquals(createDto.getName(), user.getName());
        assertEquals(createDto.getPassword(), user.getPassword());
        assertEquals(createDto.getEmail(), user.getEmail());
    }

    @Test
    void duplicateEmailSignUp() {
        UserCreateDto createDto = new UserCreateDto("kim", "w@gmail.com", "1234");
        User user = userService.signUp(createDto);

        UserCreateDto createDto2 = new UserCreateDto("kim2", "w@gmail.com", "1234");
        assertThrows(DuplicateEmailException.class, () -> userService.signUp(createDto2));
    }

    @Test
    void deleteUser(){
        UserCreateDto createDto = new UserCreateDto("kim", "w@gmail.com", "1234");
        User user = userService.signUp(createDto);

        Long id = user.getId();
        userService.deleteUser(id);
        assertThrows(UserNotFoundException.class, () -> userService.findUser(id));
    }

    @Test
    void findUser() {
        UserCreateDto createDto = new UserCreateDto("kim", "wnsgk@gmail.com", "wnsgk");
        User user = userService.signUp(createDto);

        User user1 = userService.findUser(user.getId());

        assertEquals(user1.getName(), user.getName());
        assertEquals(user1.getPassword(), user.getPassword());
        assertEquals(user1.getEmail(), user.getEmail());
    }

}