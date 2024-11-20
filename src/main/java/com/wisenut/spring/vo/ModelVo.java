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
@Schema(description = "모델 필드 정보")
public class ModelVo {

    @Schema(description = "모델별 키값")
    private String disp_cat_no;

    @Schema(description = "대분류 코드")
    private String lrg_cat_no;

    @Schema(description = "대분류명")
    private String l_cat_nm;

    @Schema(description = "중분류코드")
    private String mid_cat_no;

    @Schema(description = "중분류명")
    private String m_cat_nm;

    @Schema(description = "모델상위코드")
    private String upr_disp_cat_no;

    @Schema(description = "소분류코드")
    private String sml_cat_no;

    @Schema(description = "모델명")
    private String model_name;

    @Schema(description = "모델코드")
    private String model_cd;

    @Schema(description = "최저가격")
    private String model_amt;

    @Schema(description = "차종류")
    private String car_type;

    @Schema(description = "차이미지")
    private String model_cat_img_addr;

    @Schema(description = "")
    private String model_disp_flg;

    @Schema(description = "간편견적전시코드")
    private String carinfo_disp_cat_no;

    @Schema(description = "")
    private String eleccar_yn;

    @Schema(description = "")
    private String disp_stat_cd;

    @Schema(description = "")
    private String taxben_yn;

    @Schema(description = "")
    private String rsv_1_attr;

    @Schema(description = "")
    private String rsv_2_attr;

    @Schema(description = "쇼룸전시 순서")
    private String urp_disp_seq;

    @Schema(description = "모델전시 순서")
    private String model_disp_seq;

}
