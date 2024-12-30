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
// 정적페이지 필드 정보
public class Html {

    // 검색탭코드
    private String tabCd;

    // 검색탭명
    private String tabNm;

    // 메뉴경로명
    private String menuPathNm;

    // 메뉴URL
    private String menuPathURL;

    // DEPTH1_메뉴명
    private String menuDepth1Nm;

    // DEPTH2_메뉴명
    private String menuDepth2Nm;

    // DEPTH3_메뉴명
    private String menuDepth3Nm;

    // DEPTH4_메뉴명
    private String menuDepth4Nm;

    // DEPTH5_메뉴명
    private String menuDepth5Nm;

    // DEPTH6_메뉴명
    private String menuDepth6Nm;

    // DEPTH7_메뉴명
    private String menuDepth7Nm;

    // 정적컨텐츠
    private String txtCont;

    // 랭킹점수
    private float score;

    static public SearchVo getSearchResult(Search search, String COLLECTION) {

        List<Html> list = new ArrayList<>();

        // 전체건수, 결과건수 출력
        int totalCount = search.w3GetResultTotalCount(COLLECTION);
        int resultCount = search.w3GetResultCount(COLLECTION);

        for (int i = 0; i < resultCount; i++) {

            // 기본 검색결과 객체 생성
            String tabCd = search.w3GetField(COLLECTION, "TAB_CD", i);
            String tabNm = search.w3GetField(COLLECTION, "TAB_NM", i)
                                 .replaceAll("<!HS>", "<em class=\"keyword\">")
                                 .replaceAll("<!HE>", "</em>");
            String menuPathNm = search.w3GetField(COLLECTION, "MENU_PATH_NM", i)
                                      .replaceAll("<!HS>", "<em class=\"keyword\">")
                                      .replaceAll("<!HE>", "</em>");
            String menuPathURL = search.w3GetField(COLLECTION, "MENU_PATH_URL", i);
            String menuDepth1Nm = search.w3GetField(COLLECTION, "MENU_DEPTH1_NM", i);
            String menuDepth2Nm = search.w3GetField(COLLECTION, "MENU_DEPTH2_NM", i);
            String menuDepth3Nm = search.w3GetField(COLLECTION, "MENU_DEPTH3_NM", i);
            String menuDepth4Nm = search.w3GetField(COLLECTION, "MENU_DEPTH4_NM", i);
            String menuDepth5Nm = search.w3GetField(COLLECTION, "MENU_DEPTH5_NM", i);
            String menuDepth6Nm = search.w3GetField(COLLECTION, "MENU_DEPTH6_NM", i);
            String menuDepth7Nm = search.w3GetField(COLLECTION, "MENU_DEPTH7_NM", i);
            String txtCont = search.w3GetField(COLLECTION, "TXT_CONT", i)
                                   .replaceAll("<!HS>", "<em class=\"keyword\">")
                                   .replaceAll("<!HE>", "</em>");
            float score = search.w3GetRank(COLLECTION, i);

            Html vo = Html.builder()
                          .score(score)
                          .tabCd(tabCd)
                          .tabNm(tabNm)
                          .menuPathNm(menuPathNm)
                          .menuPathURL(menuPathURL)
                          .menuDepth1Nm(menuDepth1Nm)
                          .menuDepth2Nm(menuDepth2Nm)
                          .menuDepth3Nm(menuDepth3Nm)
                          .menuDepth4Nm(menuDepth4Nm)
                          .menuDepth5Nm(menuDepth5Nm)
                          .menuDepth6Nm(menuDepth6Nm)
                          .menuDepth7Nm(menuDepth7Nm)
                          .txtCont(txtCont)
                          .build();

            list.add(vo);
        }
        return SearchVo.<Html>builder()
                       .collection(COLLECTION)
                       .totalCount(totalCount)
                       .count(resultCount)
                       .result(list)
                       .build();
    }

}
