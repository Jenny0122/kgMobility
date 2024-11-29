package com.wisenut.spring.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.apache.commons.math3.ml.clustering.Clusterable;
import org.apache.commons.math3.ml.clustering.evaluation.ClusterEvaluator;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Schema(description = "정적페이지 필드 정보")
public class HtmlVo {

    @Schema(description = "검색탭코드")
    private String tabCd;

    @Schema(description = "검색탭명")
    private String tabNm;

    @Schema(description = "메뉴경로명")
    private String menuPathNm;

    @Schema(description = "메뉴URL")
    private String menuPathURL;

    @Schema(description = "DEPTH1_메뉴명")
    private String menuDepth1Nm;

    @Schema(description = "DEPTH2_메뉴명")
    private String menuDepth2Nm;

    @Schema(description = "DEPTH3_메뉴명")
    private String menuDepth3Nm;

    @Schema(description = "DEPTH4_메뉴명")
    private String menuDepth4Nm;

    @Schema(description = "DEPTH5_메뉴명")
    private String menuDepth5Nm;

    @Schema(description = "DEPTH6_메뉴명")
    private String menuDepth6Nm;

    @Schema(description = "DEPTH7_메뉴명")
    private String menuDepth7Nm;

    @Schema(description = "정적컨텐츠")
    private String txtCont;

    @Schema(description = "랭킹점수")
    private float score;

}
