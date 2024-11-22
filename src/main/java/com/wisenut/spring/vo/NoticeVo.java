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
@Schema(description = "공지사항 필드 정보")
public class NoticeVo {

    @Schema(description = "")
    private String bbNo;

    @Schema(description = "")
    private String rn;

    @Schema(description = "")
    private String oppbYN;

    @Schema(description = "시작날짜")
    private String strtDt;

    @Schema(description = "종료날짜")
    private String endDt;

    @Schema(description = "제목")
    private String title;

    @Schema(description = "내용")
    private String content;

    @Schema(description = "")
    private String uptnDispYN;

    @Schema(description = "등록날짜")
    private String regDt;

    @Schema(description = "")
    private String appxAppxFileNo;

    @Schema(description = "")
    private String appxRegSeq;

    @Schema(description = "")
    private String appxAppxFileKndCd;

    @Schema(description = "")
    private String appxUpldFilePath;

    @Schema(description = "")
    private String appxAppxFileNm;

    @Schema(description = "랭킹점수")
    private float score;

}
