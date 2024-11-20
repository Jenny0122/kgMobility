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
    private String bb_no;

    @Schema(description = "")
    private String oopb_yn;

    @Schema(description = "제목")
    private String title;

    @Schema(description = "내용")
    private String content;

    @Schema(description = "")
    private String bnnr_img_url;

    @Schema(description = "")
    private LocalDateTime strt_dt;

    @Schema(description = "")
    private LocalDateTime end_dt;

    @Schema(description = "")
    private LocalDateTime reg_dt;

    @Schema(description = "")
    private String appx_appx_file_no;

    @Schema(description = "")
    private String appx_upld_file_path;

    @Schema(description = "")
    private String appx_appx_file_nm;

    @Schema(description = "진행여부 code")
    private String status_cd;

}
