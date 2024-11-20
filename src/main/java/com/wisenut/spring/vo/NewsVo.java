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
@Schema(description = "보도자료 필드 정보")
public class NewsVo {

    @Schema(description = "")
    private String bb_no;

    @Schema(description = "")
    private String rn;

    @Schema(description = "")
    private String oppb_yn;

    @Schema(description = "시작날짜")
    private LocalDateTime strt_dt;

    @Schema(description = "종료날짜")
    private LocalDateTime end_dt;

    @Schema(description = "제목")
    private String title;

    @Schema(description = "내용")
    private String content;

    @Schema(description = "")
    private String uptn_disp_yn;

    @Schema(description = "등록날짜")
    private LocalDateTime reg_dt;

    @Schema(description = "등록날짜")
    private LocalDateTime site_cd;

    @Schema(description = "")
    private String appx_appx_file_no;

    @Schema(description = "")
    private String appx_reg_seq;

    @Schema(description = "")
    private String appx_upld_file_path;

    @Schema(description = "")
    private String appx_appx_file_nm;
}
