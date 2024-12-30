package com.wisenut.spring.vo;

import QueryAPI7.Search;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Builder
@AllArgsConstructor
// 모델 필드 정보
public class Model {

    // 모델별 키값
    private String dispCatNo;

    // 대분류코드
    private String lrgCatNo;

    // 대분류명
    private String lCatNm;

    // 중분류코드
    private String midCatNo;

    // 중분류명
    private String mCatNm;

    // 모델상위코드
    private String uprDispCatNo;

    // 소분류코드
    private String smlCatNo;

    // 모델명
    private String modelName;

    // 모델코드
    private String modelCd;

    // 최저가격
    private String modelAmt;

    // 차종류
    private String carType;

    // 차이미지
    private String modelCatImgAddr;

    // 전시플래그
    private String modelDispFlg;

    // 간편견적전시코드
    private String carinfoDispCatNo;

    // 전기차여부
    private String eleccarYN;

    // 전시상태코드
    private String dispStatCd;

    // 세제혜택적용여부
    private String taxbenYN;

    // 속성예약필드1
    private String rsv1Attr;

    // 속성예약필드2
    private String rsv2Attr;

    // 쇼룸전시 순서
    private String urpDispSeq;

    // 모델전시 순서
    private String modelDispSeq;

    // 랭킹점수
    private float score;

    static public SearchVo getSearchResult(Search search, String COLLECTION) {

        List<Model> list = new ArrayList<>();

        // 전체건수, 결과건수 출력
        int totalCount = search.w3GetResultTotalCount(COLLECTION);
        int resultCount = search.w3GetResultCount(COLLECTION);

        for (int i = 0; i < resultCount; i++) {

            // 기본 검색결과 객체 생성
            String dispCatNo = search.w3GetField(COLLECTION, "DISP_CAT_NO", i);
            String lrgCatNo = search.w3GetField(COLLECTION, "LRG_CAT_NO", i);
            String lCatNm = search.w3GetField(COLLECTION, "L_CAT_NM", i);
            String midCatNo = search.w3GetField(COLLECTION, "MID_CAT_NO", i);
            String mCatNm = search.w3GetField(COLLECTION, "M_CAT_NM", i);
            String uprDispCatNo = search.w3GetField(COLLECTION, "UPR_DISP_CAT_NO", i);
            String smlCatNo = search.w3GetField(COLLECTION, "SML_CAT_NO", i);
            String modelName = search.w3GetField(COLLECTION, "MODEL_NAME", i)
                                     .replaceAll("<!HS>", "<em class=\"keyword\">")
                                     .replaceAll("<!HE>", "</em>");
            String modelCd = search.w3GetField(COLLECTION, "MODEL_CD", i)
                                   .replaceAll("<!HS>", "<em class=\"keyword\">")
                                   .replaceAll("<!HE>", "</em>");
            String modelAmt = search.w3GetField(COLLECTION, "MODEL_AMT", i);
            String carType = search.w3GetField(COLLECTION, "CAR_TYPE", i);
            String modelCatImgAddr = search.w3GetField(COLLECTION, "MODEL_CAT_IMG_ADDR", i);
            String modelDispFlg = search.w3GetField(COLLECTION, "MODEL_DISP_FLG", i);
            String carinfoDispCatNo = search.w3GetField(COLLECTION, "CARINFO_DISP_CAT_NO", i);
            String eleccarYN = search.w3GetField(COLLECTION, "ELECCAR_YN", i);
            String dispStatCd = search.w3GetField(COLLECTION, "DISP_STAT_CD", i);
            String taxbenYN = search.w3GetField(COLLECTION, "TAXBEN_YN", i);
            String rsv1Attr = search.w3GetField(COLLECTION, "RSV_1_ATTR", i);
            String rsv2Attr = search.w3GetField(COLLECTION, "RSV_2_ATTR", i);
            String urpDispSeq = search.w3GetField(COLLECTION, "UPR_DISP_SEQ", i);
            String modelDispSeq = search.w3GetField(COLLECTION, "MODEL_DISP_SEQ", i);
            float score = search.w3GetRank(COLLECTION, i);


            Model vo = Model.builder()
                            .score(score)
                            .dispCatNo(dispCatNo)
                            .lrgCatNo(lrgCatNo)
                            .lCatNm(lCatNm)
                            .midCatNo(midCatNo)
                            .mCatNm(mCatNm)
                            .uprDispCatNo(uprDispCatNo)
                            .smlCatNo(smlCatNo)
                            .modelName(modelName)
                            .modelCd(modelCd)
                            .modelAmt(modelAmt)
                            .carType(carType)
                            .modelCatImgAddr(modelCatImgAddr)
                            .modelDispFlg(modelDispFlg)
                            .carinfoDispCatNo(carinfoDispCatNo)
                            .eleccarYN(eleccarYN)
                            .dispStatCd(dispStatCd)
                            .taxbenYN(taxbenYN)
                            .rsv1Attr(rsv1Attr)
                            .rsv2Attr(rsv2Attr)
                            .urpDispSeq(urpDispSeq)
                            .modelDispSeq(modelDispSeq)
                            .build();

            list.add(vo);

        }

        return SearchVo.<Model>builder()
                       .collection(COLLECTION)
                       .totalCount(totalCount)
                       .count(resultCount)
                       .result(list)
                       .build();
    }

}
