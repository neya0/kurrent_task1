package com.example.kurrant_task1.controller;

import com.example.kurrant_task1.model.dto.RequestDto;
import com.example.kurrant_task1.model.dto.ResponseDto;
import com.example.kurrant_task1.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Description;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;

    @Description("게시글 생성")
    @PostMapping("/boards/{boardId}/articles")
    public void createArticle(@PathVariable Long boardId, @RequestBody RequestDto request) {
        articleService.createArticle(boardId, request);
    }

    @Description("게시글 상세 조회")
    @GetMapping("/boards/{boardId}/articles/{articleId}")
    public ResponseDto retrieveArticle(@PathVariable Long boardId, @PathVariable Long articleId) {
        return articleService.retrieveArticle(boardId, articleId);
    }

    @Description("게시글 목록 조회")
    @GetMapping("/articles")
    public List<ResponseDto> getArticleList() {
        return articleService.getArticleList();
    }

    @Description("게시판 별 게시글 목록 조회")
    @GetMapping("/boards/{boardId}/articles")
    public List<ResponseDto> getArticleListByBoard(@PathVariable Long boardId) {
        return articleService.getArticleListByBoard(boardId);
    }

    @Description("게시글 삭제")
    @DeleteMapping("/boards/{boardId}/articles/{articleId}")
    public void deleteArticle(@PathVariable Long boardId, @PathVariable Long articleId) {
        articleService.deleteArticle(boardId, articleId);
    }

    @Description("게시글 단어로 검색")
    @GetMapping("/articles/search")
    public List<ResponseDto> searchArticleByKeyword(@RequestParam(defaultValue = "") String keyword) {
        return articleService.searchArticleByKeyword(keyword);
    }

    @Description("특정 기간 내 게시글 조회")
    @GetMapping("/articles/date")
    public List<ResponseDto> searchArticleByDate(@RequestParam(defaultValue = "") String startDate,
                                                 @RequestParam(defaultValue = "") String endDate) {
        return articleService.searchArticleByDate(startDate, endDate);
    }

}
