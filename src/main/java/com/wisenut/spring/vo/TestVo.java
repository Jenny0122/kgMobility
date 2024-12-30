package com.wisenut.spring.vo;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class TestVo<T> { // 통합 검색 필드 정보

    private String collection;

    @Builder.Default
    private int totalCount = 0;

    @Builder.Default
    private int count = 0;

    private List<T> result;
}

