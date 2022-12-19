package com.example.kurrant_task1.model.dto;

import com.example.kurrant_task1.model.Article;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDto {
    private Long articleId;
    private String nameKo;
    private LocalDate createdDate;
    private Boolean isPinned;
    private int viewCount;
    private String title;
    private String contentHtml;
    private String contentString;

    public static ResponseDto responseDtoRetrieve(Article article, String nameKo) {
        return ResponseDto.builder().
                articleId(article.getArticle_id()).
                title(article.getTitle()).
                contentHtml(article.getContent_html()).
                viewCount(article.getView_count()).
                isPinned(article.getIs_pinned()).
                createdDate(article.getCreated_date()).
                contentString(article.getContent_string()).
                nameKo(nameKo).
                build();
    }

    public static ResponseDto responseDtoList(Article article) {
        return ResponseDto.builder().
                articleId(article.getArticle_id()).
                title(article.getTitle()).
                viewCount(article.getView_count()).
                isPinned(article.getIs_pinned()).
                createdDate(article.getCreated_date()).
                contentString(article.getContent_string()).
                build();
    }
}
