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
    private String dispCatNo;

    @Schema(description = "대분류코드")
    private String lrgCatNo;

    @Schema(description = "대분류명")
    private String lCatNm;

    @Schema(description = "중분류코드")
    private String midCatNo;

    @Schema(description = "중분류명")
    private String mCatNm;

    @Schema(description = "모델상위코드")
    private String uprDispCatNo;

    @Schema(description = "소분류코드")
    private String smlCatNo;

    @Schema(description = "모델명")
    private String modelName;

    @Schema(description = "모델코드")
    private String modelCd;

    @Schema(description = "최저가격")
    private String modelAmt;

    @Schema(description = "차종류")
    private String carType;

    @Schema(description = "차이미지")
    private String modelCatImgAddr;

    @Schema(description = "")
    private String modelDispFlg;

    @Schema(description = "간편견적전시코드")
    private String carinfoDispCatNo;

    @Schema(description = "")
    private String eleccarYN;

    @Schema(description = "")
    private String dispStatCd;

    @Schema(description = "")
    private String taxbenYN;

    @Schema(description = "")
    private String rsv1Attr;

    @Schema(description = "")
    private String rsv2Attr;

    @Schema(description = "쇼룸전시 순서")
    private String urpDispSeq;

    @Schema(description = "모델전시 순서")
    private String modelDispSeq;

    @Schema(description = "랭킹점수")
    private float score;

}
