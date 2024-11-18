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

    SearchVo<T> news;

    SearchVo<T> event;

    SearchVo<T> notice;
}
