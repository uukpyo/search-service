package com.search.multi.controller.Rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.search.multi.SearchApiServerApplication;
import com.search.multi.data.dto.search.BlogRequestDto;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class RestBlogControllerTest {

    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;

    @Nested
    @DisplayName("성공케이스")
    class SuccessCase {

        @Test
        void blogSearch() throws Exception {
            // given
            BlogRequestDto blogRequestDto = new BlogRequestDto();
            blogRequestDto.setQuery("검색어");
            blogRequestDto.setPage(1);
            blogRequestDto.setSort("recency");

            // when
            ResultActions resultActions = mockMvc.perform(get("/blog/v1/search")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(blogRequestDto))
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print());

            // then
            resultActions
                    .andExpect(status().isOk());
        }

        @Test
        void blogSearchMost() throws Exception {
            // when
            ResultActions resultActions = mockMvc.perform(get("/blog/v1/search/most")
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print());

            // then
            resultActions
                    .andExpect(status().isOk());
        }
    }

    @Nested
    @DisplayName("실패케이스")
    class FailCase {

        @Test
        @DisplayName("DTO Query Null")
        void blogSearch() throws Exception {
            // given
            BlogRequestDto blogRequestDto = new BlogRequestDto();
            //blogRequestDto.setQuery("검색어");
            blogRequestDto.setPage(1);
            blogRequestDto.setSort("recency");

            // when
            ResultActions resultActions = mockMvc.perform(get("/blog/v1/search")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(blogRequestDto))
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print());

            // then
            resultActions
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("status").value("FAIL"));
        }
    }
}