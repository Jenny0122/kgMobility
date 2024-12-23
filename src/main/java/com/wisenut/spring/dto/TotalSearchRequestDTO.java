package com.wisenut.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
public class TotalSearchRequestDTO {

    @NotNull(message = "검색어는 '필수'값입니다.")
    private String query;

    @NotNull(message = "collection은 '필수'값 입니다.")
    private String collection;

    private String sortField;

    private String sortDirection;

    private int pageStart;

    private int count;

    public TotalSearchRequestDTO() {
        pageStart = 0;
        count = 10;
    }
}
