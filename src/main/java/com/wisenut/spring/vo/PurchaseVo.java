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
@Schema(description = "구매 필드 정보")
public class PurchaseVo {

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

    @Schema(description = "소분류코드")
    private String sml_cat_no;

    @Schema(description = "소분류명")
    private String s_cat_nm;

    @Schema(description = "모델상위코드")
    private String upr_disp_cat_no;

    @Schema(description = "모델코드")
    private String dtl_cat_no;

    @Schema(description = "모델명")
    private String model_name;

    @Schema(description = "모델코드")
    private String model_cd;

    @Schema(description = "최저가격")
    private String model_amt;

    @Schema(description = "차종류")
    private String car_type;

    @Schema(description = "클릭시이동 URL")
    private String od_url;

    @Schema(description = "모델정보")
    private String model_info;

    @Schema(description = "엔진정보")
    private String engine_info;

    @Schema(description = "이미지파일명")
    private String model_img_addr;

    @Schema(description = "대표이미지명")
    private String model_main_img_addr;

    @Schema(description = "썸네일이미지명")
    private String model_thmn_img_addr;

    @Schema(description = "마케팅문구")
    private String mktg_txt;

    @Schema(description = "월혜택")
    private String benefits_month;

    @Schema(description = "스페셜혜택")
    private String special_benefits;

    @Schema(description = "카탈로그명칭")
    private String catalog_file_name;

    @Schema(description = "카탈로그파일명")
    private String catalog_addr;

    @Schema(description = "e카탈로그 URL")
    private String e_catalog_lnk_url_addr;

    @Schema(description = "가격표명칭")
    private String price_list_file_name;

    @Schema(description = "가격표파일명")
    private String price_list_addr;

    @Schema(description = "액세서리명")
    private String accessories_file_name;

    @Schema(description = "엑세서리파일명")
    private String accessories_addr;

    @Schema(description = "PC배너파일명")
    private String banner_pc_img_addr;

    @Schema(description = "모바일배너파일명")
    private String banner_mo_img_addr;

    @Schema(description = "전시순서")
    private String disp_seq;


}
