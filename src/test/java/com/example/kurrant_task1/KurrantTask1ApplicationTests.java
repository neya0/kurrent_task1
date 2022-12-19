package com.example.kurrant_task1;

import com.example.kurrant_task1.model.Article;
import com.example.kurrant_task1.model.dto.RequestDto;
import com.example.kurrant_task1.repository.ArticleMapper;
import com.example.kurrant_task1.service.ArticleService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class KurrantTask1ApplicationTests {

    @Autowired
    ArticleMapper articleMapper;

    @Autowired
    ArticleService articleService;

    @Test
    @Transactional
    @DisplayName("게시물 생성")
    void insertTest() {
        RequestDto request = new RequestDto("테스트", "테스트 게시글");
        Article article = new Article(1L, request);
        int result = articleMapper.createArticle(article);
        assertEquals(1, result);
    }

    @Test
    @Transactional
    @DisplayName("게시물 상세조회")
    void retrieveTest() {
        String str = "테스트입니다.";
        RequestDto request = new RequestDto(str, "테스트 게시글");
        Article article = new Article(1L, request);

        articleMapper.createArticle(article);
        List<Article> responses = articleMapper.searchArticleByKeyword(str);
        Article article2 = responses.get(0);

        assertEquals(request.getTitle(), article2.getTitle());
        assertEquals(request.getContent(), article2.getContent_html());
        assertEquals(request.getContent(), article2.getContent_string());
        assertEquals(article.getBoard_id(), article2.getBoard_id());

    }

    @Test
    @Transactional
    @DisplayName("게시물 목록 조회")
    void getArticleList() {
        int oldListSize = articleMapper.getArticleList().size();

        RequestDto request = new RequestDto("목록 조회 테스트", "테스트 게시글");
        Article article = new Article(1L, request);
        articleMapper.createArticle(article);

        int recentListSize = articleMapper.getArticleList().size();

        assertEquals(recentListSize, oldListSize + 1);

    }

    @Test
    @Transactional
    @DisplayName("게시판 별 게시물 목록 조회")
    void getArticleListByBoard() {
        int oldListSize = articleMapper.getArticleListByBoardId(1L).size();

        RequestDto request = new RequestDto("목록 조회 테스트", "테스트 게시글");
        Article article = new Article(1L, request);
        articleMapper.createArticle(article);

        int recentListSize = articleMapper.getArticleListByBoardId(1L).size();

        assertEquals(recentListSize, oldListSize + 1);

    }

    @Test
    @Transactional
    @DisplayName("게시물 삭제")
    void deleteArticle() {
        RequestDto request = new RequestDto("단위 테스트1", "테스트 게시글1");
        Article article = new Article(1L, request);
        articleMapper.createArticle(article);

        RequestDto request2 = new RequestDto("단위 테스트2", "테스트 게시글2");
        Article article2 = new Article(1L, request2);
        articleMapper.createArticle(article2);

        RequestDto request3 = new RequestDto("단위 테스트3", "테스트 게시글3");
        Article article3 = new Article(1L,request3);
        articleMapper.createArticle(article3);

        int oldListSize = articleMapper.getArticleList().size();
        List<Article> articles = articleMapper.getArticleList();

        articleMapper.deleteArticle(1L, articles.get(0).getArticle_id());
        articleMapper.deleteArticle(1L, articles.get(1).getArticle_id());

        int recentListSize = articleMapper.getArticleList().size();
        assertEquals(recentListSize, oldListSize - 2);

        articleMapper.deleteArticle(1L, articles.get(2).getArticle_id());
        recentListSize = articleMapper.getArticleList().size();
        assertEquals(recentListSize, oldListSize - 3);

    }

    @Test
    @Transactional
    @DisplayName("게시물 검색 (단어)")
    void searchKeywordTest() {
        String str = "테스트입니다.";
        RequestDto request = new RequestDto(str, "테스트 게시글");
        Article article = new Article(1L, request);

        articleMapper.createArticle(article);
        List<Article> responses = articleMapper.searchArticleByKeyword(str);
        List<Article> responses2 = articleMapper.searchArticleByKeyword("");

        assertTrue(responses.size() > 0);

    }

    @Test
    @Transactional
    @DisplayName("게시물 검색 (단어) - 예외")
    void searchKeywordExceptionTest() {

        assertThatThrownBy(() -> articleService.searchArticleByKeyword(""))
                .isInstanceOf(NullPointerException.class);

    }

    @Test
    @Transactional
    @DisplayName("특정 기간 게시물 검색")
    void searchDateTest() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime minAfter = LocalDateTime.now().plusMinutes(3);
        int oldListSize = articleMapper.searchArticleByDate(now, minAfter).size();

        RequestDto request = new RequestDto("단위 테스트1", "테스트 게시글1");
        Article article = new Article(1L, request);
        articleMapper.createArticle(article);

        RequestDto request2 = new RequestDto("단위 테스트2", "테스트 게시글2");
        Article article2 = new Article(1L, request2);
        articleMapper.createArticle(article2);

        RequestDto request3 = new RequestDto("단위 테스트3", "테스트 게시글3");
        Article article3 = new Article(1L, request3);
        articleMapper.createArticle(article3);

        int recentListSize = articleMapper.searchArticleByDate(now, minAfter).size();
        assertEquals(recentListSize, oldListSize + 3);

    }

    @Test
    @DisplayName("특정 기간 게시물 검색 - 예외")
    void searchDateExceptionTest() {
        String now = "";
        String minAfter = "";

        assertThatThrownBy(() -> articleService.searchArticleByDate(now, minAfter))
                .isInstanceOf(NullPointerException.class);
    }
}
