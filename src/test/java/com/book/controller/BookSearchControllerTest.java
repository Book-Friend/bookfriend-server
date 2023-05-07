package com.book.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class BookSearchControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private WebApplicationContext applicationContext;

    @BeforeEach
    public void init() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(applicationContext)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .build();
    }

    @Test
    @DisplayName("책 검색")
    void searchSuccess() throws Exception {
        mockMvc.perform(get("/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("keyword", "데미안")
                        .param("start", "1"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("키워드 오류")
    void searchFailKeyword() throws Exception {
        Map<String, String> error = new HashMap<>();
        error.put("keyword", "Required request parameter 'keyword' for method parameter type String is not present");
        String response = objectMapper.writeValueAsString(error);

        mockMvc.perform(get("/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("start", "1"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(response));
    }

    @Test
    @DisplayName("검색 페이지 오류")
    void searchFailStart() throws Exception {
        Map<String, String> error = new HashMap<>();
        error.put("start", "Required request parameter 'start' for method parameter type int is not present");
        String response = objectMapper.writeValueAsString(error);

        mockMvc.perform(get("/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("keyword", "해리포터"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(response));
    }

    @Test
    @DisplayName("isbn 오류")
    void searchDetailFailIsbn() throws Exception {
        Map<String, String> error = new HashMap<>();
        error.put("isbn", "Required request parameter 'isbn' for method parameter type String is not present");
        String response = objectMapper.writeValueAsString(error);

        mockMvc.perform(get("/search/detail")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(response));
    }

}
