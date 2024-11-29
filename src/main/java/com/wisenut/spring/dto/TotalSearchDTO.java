package com.wisenut.spring.dto;

import com.wisenut.spring.vo.SearchVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class TotalSearchDTO<T> {

    String query;

    SearchVo<T> purchase;

    SearchVo<T> model;

//    SearchVo<T> news;
//
//    SearchVo<T> event;
//
//    SearchVo<T> notice;

    SearchVo<T> customer;

    SearchVo<T> brand;

    SearchVo<T> about;

    SearchVo<T> apply;

    SearchVo<T> audit;

    SearchVo<T> pressEvent;

    // String recentWord;
}
