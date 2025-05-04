//package com.teamflow.forestory_be.article.presentation.controller;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.mockito.BDDMockito.given;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.teamflow.forestory_be.article.application.dto.CreateArticleCommand;
//import com.teamflow.forestory_be.article.application.service.ArticleService;
//import com.teamflow.forestory_be.article.presentation.request.CreateArticleRequest;
//
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//@WebMvcTest(ArticleController.class)
//class ArticleControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private ArticleService articleService;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Test
//    @DisplayName("아티클 등록 API - 성공")
//    void should_createArticle_success() throws Exception {
//        // given
//        CreateArticleRequest request = new CreateArticleRequest(
//                10L,
//                "제목",
//                "부제목",
//                "본문 내용",
//                "https://image.com/thumbnail.png",
//                false
//        );
//
//        given(articleService.create(new CreateArticleCommand(
//                request.authorId(),
//                request.title(),
//                request.subtitle(),
//                request.content(),
//                request.thumbnailUrl(),
//                request.isDraft()
//        ))).willReturn(1L);
//
//        // when & then
//        mockMvc.perform(post("/api/v1/articles")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.articleId").value(1L));
//
//    }
//
//    @Test
//    @DisplayName("아티클 임시저장 API - 성공")
//    void should_draftArticle_success() throws Exception {
//        // given
//        CreateArticleRequest request = new CreateArticleRequest(
//                10L,
//                "제목",
//                "부제목",
//                "본문 내용",
//                "https://image.com/thumbnail.png",
//                true
//
//        );
//
//        given(articleService.create(new CreateArticleCommand(
//                request.authorId(),
//                request.title(),
//                request.subtitle(),
//                request.content(),
//                request.thumbnailUrl(),
//                request.isDraft()
//        ))).willReturn(2L);
//
//        // when & then
//        mockMvc.perform(post("/api/v1/articles/draft")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.articleId").value(2L));
//
//    }
//
//    @Test
//    @DisplayName("아티클 등록 API - 실패(필수값누락)")
//    void should_createArticle_fail_missingField() throws Exception {
//        //given
//        CreateArticleRequest request = new CreateArticleRequest(
//                null,
//                "제목",
//                "부제목",
//                "본문 내용",
//                "https://image.com/thumbnail.png",
//                true
//
//        );
//
//        //when & then
//        mockMvc.perform(post("/api/v1/articles")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isBadRequest());
//
//    }
//
//}
