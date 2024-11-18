package com.wisenut.spring.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Builder
@AllArgsConstructor
//@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "통합 검색 필드 정보")
public class SearchVo<T> {

    String collection;

    @Builder.Default int totalCount = 0;

    @Builder.Default int count = 0;

    T result;
}

