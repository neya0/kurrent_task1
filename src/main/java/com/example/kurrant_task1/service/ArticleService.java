package com.example.kurrant_task1.service;

import com.example.kurrant_task1.model.Article;
import com.example.kurrant_task1.model.dto.RequestDto;
import com.example.kurrant_task1.model.dto.ResponseDto;
import com.example.kurrant_task1.repository.ArticleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleMapper articleMapper;

    /*
    * 게시글 생성
    */
    public void createArticle(Long id, RequestDto requestDto) {
        Article article = new Article(id, requestDto);
        articleMapper.createArticle(article);
    }

    /*
    * 게시글 상세 조회
    */
    public ResponseDto retrieveArticle(Long boardId, Long articleId) {
        Article article = articleMapper.retrieveArticle(boardId, articleId);
        String nameKo = articleMapper.getBoardName(boardId);
        articleMapper.plusViewCount(articleId);

        return ResponseDto.responseDtoRetrieve(article,nameKo);
    }

    /*
     * 게시글 목록 조회
     */
    public List<ResponseDto> getArticleList() {
        List<Article> articles = articleMapper.getArticleList();
        List<ResponseDto> requestDtos = new ArrayList<>();

        for(Article article : articles) {
            ResponseDto responseDto = ResponseDto.responseDtoList(article);
            requestDtos.add(responseDto);
        }

        return requestDtos;
    }

    /*
     * 게시판 별 게시글 목록 조회
     */
    public List<ResponseDto> getArticleListByBoard(Long boardId) {
        List<Article> articles = articleMapper.getArticleListByBoardId(boardId);
        List<ResponseDto> requestDtos = new ArrayList<>();

        for(Article article : articles) {
            ResponseDto responseDto = ResponseDto.responseDtoList(article);
            requestDtos.add(responseDto);
        }

        return requestDtos;
    }

    /*
    * 게시글 삭제
    */
    public void deleteArticle(Long boardId, Long articleId) {
        articleMapper.deleteArticle(boardId, articleId);
    }

    /*
    * 게시글 검색 - 단어
    */
    public List<ResponseDto> searchArticleByKeyword(String keyword) {
        if(keyword.isBlank() || keyword.isEmpty()) throw new NullPointerException("검색어를 입력해주세요.");

        List<Article> articles = articleMapper.searchArticleByKeyword(keyword);
        List<ResponseDto> responseDtoList = new ArrayList<>();

        for(Article article : articles) {
            ResponseDto responseDto = ResponseDto.responseDtoList(article);
            responseDtoList.add(responseDto);
        }

        return responseDtoList;
    }

    /*
    * 게시글 검색 - 특정 기간
    */
    public List<ResponseDto> searchArticleByDate(String startDate, String endDate) {
        if(startDate.isBlank() && endDate.isBlank() || startDate.isEmpty() &&  endDate.isEmpty()) {
            throw new NullPointerException("기간을 입력해주세요.");
        }

        LocalDateTime startDateTime = LocalDate.of(
                        Integer.parseInt(startDate.substring(0, 4)), // 년
                        Integer.parseInt(startDate.substring(4, 6)), // 월
                        Integer.parseInt(startDate.substring(6, 8))) // 일
                        .atTime(LocalTime.MIDNIGHT);

        LocalDateTime endDateTime = LocalDate.of(
                        Integer.parseInt(endDate.substring(0, 4)), // 년
                        Integer.parseInt(endDate.substring(4, 6)), // 월
                        Integer.parseInt(endDate.substring(6, 8))) // 일
                        .atTime(LocalTime.MAX);

        List<Article> articles = articleMapper.searchArticleByDate(startDateTime, endDateTime);
        List<ResponseDto> requestDtoList = new ArrayList<>();

        for(Article article : articles) {
            ResponseDto responseDto = ResponseDto.responseDtoList(article);
            requestDtoList.add(responseDto);
        }

        return requestDtoList;
    }

}
