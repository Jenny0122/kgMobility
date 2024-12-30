package com.wisenut.spring.dto;

import com.wisenut.spring.vo.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TotalSearchResponseDTO {

    String query;

    SearchVo<Purchase> purchase;

    SearchVo<Model> model;

    SearchVo<News> news;

    SearchVo<Event> event;

    SearchVo<Notice> notice;

    SearchVo<Html> customer;

    SearchVo<Html> brand;

    SearchVo<Html> about;

    SearchVo<Html> apply;

    SearchVo<Html> audit;

 //   SearchVo<T> pressEvent;

    // String recentWord;
}
