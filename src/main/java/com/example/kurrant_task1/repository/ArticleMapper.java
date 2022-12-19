package com.example.kurrant_task1.repository;

import com.example.kurrant_task1.model.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
@Repository
public interface ArticleMapper {
    int createArticle(@Param("article") Article article);

    List<Article> getArticleList();
    List<Article> getArticleListByBoardId(Long boardId);

    String getBoardName(Long boardId);

    Article retrieveArticle(Long boardId, Long articleId);

    void plusViewCount(Long articleId);

    void deleteArticle(Long boardId, Long articleId);

    List<Article> searchArticleByKeyword(String keyword);

    List<Article> searchArticleByDate(LocalDateTime startDateTime, LocalDateTime endDateTime);
}
