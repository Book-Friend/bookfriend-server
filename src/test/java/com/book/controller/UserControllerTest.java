package com.book.controller;

import com.book.domain.user.dto.request.LoginDto;
import com.book.domain.user.User;
import com.book.domain.user.dto.request.UserCreateDto;
import com.book.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired private UserService userService;
    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;


    @Test
    @DisplayName("회원가입 컨트롤러 테스트")
    void signUpController() throws Exception {
        UserCreateDto createDto = new UserCreateDto("kim", "new@new.com", "12341234");

        String jsonString = objectMapper.writeValueAsString(createDto);
        mockMvc.perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("로그인 컨트롤러")
    void login() throws Exception {
        User user = createUser("kim", "new@new.com", "12341234");
        LoginDto loginDto = new LoginDto(user.getEmail(), user.getPassword());
        String jsonString = objectMapper.writeValueAsString(loginDto);
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString))
                .andExpect(status().isOk());
    }

    User createUser(String name, String email, String password){
        UserCreateDto createDto = new UserCreateDto(name, email, password);
        User user = userService.signUp(createDto);
        return user;
    }

}