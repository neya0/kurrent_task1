package com.example.kurrant_task1.model;

import com.example.kurrant_task1.model.dto.RequestDto;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Article {

    private Long article_id;
    private Long board_id;
    private LocalDate created_date;
    private Boolean is_pinned;
    private int view_count;
    private String title;
    private String content_html;
    private String content_string;

    public Article(Long boardId, RequestDto requestDto) {
        this.board_id = boardId;
        this.title = requestDto.getTitle();
        this.content_html = requestDto.getContent();
        this.content_string = requestDto.getContent();
    }
}
