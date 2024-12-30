package com.wisenut.spring.vo;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Builder
@AllArgsConstructor
// 공지사항 필드 정보
public class PressEvent {

    //
    private String bbNo;

    //
    private String oppbYN;

    // 시작날짜
    private String strtDt;

    // 종료날짜
    private String endDt;

    // 제목
    private String title;

    // 내용
    private String content;

    // 등록날짜
    private String regDt;

    //
    private String appxAppxFileNo;

    //
    private String appxUpldFilePath;

    //
    private String appxAppxFileNm;

    //
    private String alias;

    // 랭킹점수
    private float score;

}
