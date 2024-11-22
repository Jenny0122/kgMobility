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
    private String dispCatNo;

    @Schema(description = "대분류코드")
    private String lrgCatNo;

    @Schema(description = "대분류명")
    private String lCatNm;

    @Schema(description = "중분류코드")
    private String midCatNo;

    @Schema(description = "중분류명")
    private String mCatNm;

    @Schema(description = "소분류코드")
    private String smlCatNo;

    @Schema(description = "소분류명")
    private String sCatNm;

    @Schema(description = "모델상위코드")
    private String uprDispCatNo;

    @Schema(description = "모델코드")
    private String dtlCatNo;

    @Schema(description = "모델명")
    private String modelName;

    @Schema(description = "모델코드")
    private String modelCd;

    @Schema(description = "최저가격")
    private String modelAmt;

    @Schema(description = "차종류")
    private String carType;

    @Schema(description = "클릭시이동 URL")
    private String odUrl;

    @Schema(description = "모델정보")
    private String modelInfo;

    @Schema(description = "엔진정보")
    private String engineInfo;

    @Schema(description = "이미지파일명")
    private String modelImgAddr;

    @Schema(description = "대표이미지명")
    private String modelMainImgAddr;

    @Schema(description = "썸네일이미지명")
    private String modelThmnImgAddr;

    @Schema(description = "마케팅문구")
    private String mktgTxt;

    @Schema(description = "월혜택")
    private String benefitsMonth;

    @Schema(description = "스페셜혜택")
    private String specialBenefits;

    @Schema(description = "카탈로그명칭")
    private String catalogFileName;

    @Schema(description = "카탈로그파일명")
    private String catalogAddr;

    @Schema(description = "e카탈로그 URL")
    private String eCatalogLnkUrlAddr;

    @Schema(description = "가격표명칭")
    private String priceListFileName;

    @Schema(description = "가격표파일명")
    private String priceListAddr;

    @Schema(description = "액세서리명")
    private String accessoriesFileName;

    @Schema(description = "엑세서리파일명")
    private String accessoriesAddr;

    @Schema(description = "PC배너파일명")
    private String bannerPcImgAddr;

    @Schema(description = "모바일배너파일명")
    private String bannerMoImgAddr;

    @Schema(description = "전시순서")
    private String dispSeq;

    private float score;


}
