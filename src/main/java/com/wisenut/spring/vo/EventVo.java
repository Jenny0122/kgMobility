package com.wisenut.spring.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Schema(description = "이벤트 필드 정보")
public class EventVo {

    @Schema(description = "")
    private String bbNo;

    @Schema(description = "")
    private String oppbYN;

    @Schema(description = "제목")
    private String title;

    @Schema(description = "내용")
    private String content;

    @Schema(description = "배너이미지 URL")
    private String bnnrImgUrl;

    @Schema(description = "시작날짜")
    private String strtDt;

    @Schema(description = "종료날짜")
    private String endDt;

    @Schema(description = "등록날짜")
    private String regDt;

    @Schema(description = "첨부파일번호")
    private String appxAppxFileNo;

    @Schema(description = "업로드파일경로")
    private String appxUpldFilePath;

    @Schema(description = "업로드파일명")
    private String appxAppxFileNm;

    @Schema(description = "진행여부 code")
    private String statusCd;

    @Schema(description = "랭킹점수")
    private float score;

}
