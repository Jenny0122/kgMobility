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
// 구매 필드 정보
public class Purchase {

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

    // 소분류코드
    private String smlCatNo;

    // 소분류명
    private String sCatNm;

    // 모델상위코드
    private String uprDispCatNo;

    // 모델코드
    private String dtlCatNo;

    // 모델명
    private String modelName;

    // 모델코드
    private String modelCd;

    // 최저가격
    private String modelAmt;

    // 차종류
    private String carType;

    // 클릭시이동 URL
    private String odUrl;

    // 모델정보
    private String modelInfo;

    // 엔진정보
    private String engineInfo;

    // 이미지파일명
    private String modelImgAddr;

    // 대표이미지명
    private String modelMainImgAddr;

    // 썸네일이미지명
    private String modelThmnImgAddr;

    // 마케팅문구
    private String mktgTxt;

    // 월혜택
    private String benefitsMonth;

    // 스페셜혜택
    private String specialBenefits;

    // 카탈로그명칭
    private String catalogFileName;

    // 카탈로그파일명
    private String catalogAddr;

    // e카탈로그 URL
    private String eCatalogLnkUrlAddr;

    // 가격표명칭
    private String priceListFileName;

    // 가격표파일명
    private String priceListAddr;

    // 액세서리명
    private String accessoriesFileName;

    // 엑세서리파일명
    private String accessoriesAddr;

    // PC배너파일명
    private String bannerPcImgAddr;

    // 모바일배너파일명")
    private String bannerMoImgAddr;

    // 전시순서")
    private String dispSeq;

    private float score;

    static public SearchVo getSearchResult(Search search, String COLLECTION) {

        List<Purchase> list = new ArrayList<>();

        // 전체건수, 결과건수 출력
        int totalCount = search.w3GetResultTotalCount(COLLECTION);
        int resultCount = search.w3GetResultCount(COLLECTION);

        for (int i = 0; i < resultCount; i++) {

            // 기본 검색결과 객체 생성
            String dispCatNo = search.w3GetField(COLLECTION, "DISP_CAT_NO", i);
            String lrgCatNo = search.w3GetField(COLLECTION, "LRG_CAT_NO", i);
            String lCatNm = search.w3GetField(COLLECTION, "L_CAT_NM", i);
            String midCatNo = search.w3GetField(COLLECTION, "MID_CAT_NO", i);
            String mCatNm = search.w3GetField(COLLECTION, "M_CAT_NM", i)
                                  .replaceAll("<!HS>", "<em class=\"keyword\">")
                                  .replaceAll("<!HE>", "</em>");
            String smlCatNo = search.w3GetField(COLLECTION, "SML_CAT_NO", i);
            String sCatNm = search.w3GetField(COLLECTION, "S_CAT_NM", i)
                                  .replaceAll("<!HS>", "<em class=\"keyword\">")
                                  .replaceAll("<!HE>", "</em>");
            String uprDispCatNo = search.w3GetField(COLLECTION, "UPR_DISP_CAT_NO", i);
            String dtlCatNo = search.w3GetField(COLLECTION, "DTL_CAT_NO", i);
            String modelName = search.w3GetField(COLLECTION, "MODEL_NAME", i)
                                     .replaceAll("<!HS>", "<em class=\"keyword\">")
                                     .replaceAll("<!HE>", "</em>");
            String modelCd = search.w3GetField(COLLECTION, "MODEL_CD", i)
                                   .replaceAll("<!HS>", "<em class=\"keyword\">")
                                   .replaceAll("<!HE>", "</em>");
            String modelAmt = search.w3GetField(COLLECTION, "MODEL_AMT", i);
            String carType = search.w3GetField(COLLECTION, "CAR_TYPE", i);
            String odUrl = search.w3GetField(COLLECTION, "OD_URL", i);
            String modelInfo = search.w3GetField(COLLECTION, "MODEL_INFO", i)
                                     .replaceAll("<!HS>", "<em class=\"keyword\">")
                                     .replaceAll("<!HE>", "</em>");
            String engineInfo = search.w3GetField(COLLECTION, "ENGINE_INFO", i)
                                      .replaceAll("<!HS>", "<em class=\"keyword\">")
                                      .replaceAll("<!HE>", "</em>");
            String modelImgAddr = search.w3GetField(COLLECTION, "MODEL_IMG_ADDR", i);
            String modelMainImgAddr = search.w3GetField(COLLECTION, "MODEL_MAIN_IMG_ADDR", i);
            String modelThmnImgAddr = search.w3GetField(COLLECTION, "MODEL_THMN_IMG_ADDR", i);
            String mktgTxt = search.w3GetField(COLLECTION, "MKTG_TXT", i);
            String benefitsMonth = search.w3GetField(COLLECTION, "BENEFITS_MONTH", i)
                                         .replaceAll("<!HS>", "<em class=\"keyword\">")
                                         .replaceAll("<!HE>", "</em>");
            String specialBenefits = search.w3GetField(COLLECTION, "SPECIAL_BENEFITS", i)
                                           .replaceAll("<!HS>", "<em class=\"keyword\">")
                                           .replaceAll("<!HE>", "</em>");
            String catalogFileName = search.w3GetField(COLLECTION, "CATALOG_FILE_NAME", i);
            String catalogAddr = search.w3GetField(COLLECTION, "CATALOG_ADDR", i);
            String eCatalogLnkUrlAddr = search.w3GetField(COLLECTION, "E_CATALOG_LNK_URL_ADDR", i);
            String priceListFileName = search.w3GetField(COLLECTION, "PRICE_LIST_FILE_NAME", i);
            String priceListAddr = search.w3GetField(COLLECTION, "PRICE_LIST_ADDR", i);
            String accessoriesFileName = search.w3GetField(COLLECTION, "ACCESSORIES_FILE_NAME", i);
            String accessoriesAddr = search.w3GetField(COLLECTION, "ACCESSORIES_ADDR", i);
            String bannerPcImgAddr = search.w3GetField(COLLECTION, "BANNER_PC_IMG_ADDR", i);
            String bannerMoImgAddr = search.w3GetField(COLLECTION, "BANNER_MO_IMG_ADDR", i);
            String dispSeq = search.w3GetField(COLLECTION, "DISP_SEQ", i);
            float score = search.w3GetRank(COLLECTION, i);


            Purchase vo = Purchase.builder()
                                  .score(score)
                                  .dispCatNo(dispCatNo)
                                  .lrgCatNo(lrgCatNo)
                                  .lCatNm(lCatNm)
                                  .midCatNo(midCatNo)
                                  .mCatNm(mCatNm)
                                  .smlCatNo(smlCatNo)
                                  .sCatNm(sCatNm)
                                  .uprDispCatNo(uprDispCatNo)
                                  .dtlCatNo(dtlCatNo)
                                  .modelName(modelName)
                                  .modelCd(modelCd)
                                  .modelAmt(modelAmt)
                                  .carType(carType)
                                  .odUrl(odUrl)
                                  .modelInfo(modelInfo)
                                  .engineInfo(engineInfo)
                                  .modelImgAddr(modelImgAddr)
                                  .modelMainImgAddr(modelMainImgAddr)
                                  .modelThmnImgAddr(modelThmnImgAddr)
                                  .mktgTxt(mktgTxt)
                                  .benefitsMonth(benefitsMonth)
                                  .specialBenefits(specialBenefits)
                                  .catalogFileName(catalogFileName)
                                  .catalogAddr(catalogAddr)
                                  .eCatalogLnkUrlAddr(eCatalogLnkUrlAddr)
                                  .priceListFileName(priceListFileName)
                                  .priceListAddr(priceListAddr)
                                  .accessoriesFileName(accessoriesFileName)
                                  .accessoriesAddr(accessoriesAddr)
                                  .bannerPcImgAddr(bannerPcImgAddr)
                                  .bannerMoImgAddr(bannerMoImgAddr)
                                  .dispSeq(dispSeq)
                                  .build();

            list.add(vo);

        }

        return SearchVo.<Purchase>builder()
                       .collection(COLLECTION)
                       .totalCount(totalCount)
                       .count(resultCount)
                       .result(list)
                       .build();
    }
}
