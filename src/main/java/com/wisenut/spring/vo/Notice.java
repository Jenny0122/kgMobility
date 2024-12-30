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

public class Notice {// 공지사항 필드 정보

    //
    private String bbNo;

    //
    private String rn;

    //
    private String oppbYN;

    // 시작날짜
    private String strtDt;

    // 종료날짜
    private String endDt;

    // 제목
    private String title;

    // 내용
    private String content;

    //
    private String uptnDispYN;

    // 등록날짜
    private String regDt;

    //
    private String appxAppxFileNo;

    //
    private String appxRegSeq;

    //
    private String appxAppxFileKndCd;

    //
    private String appxUpldFilePath;

    //
    private String appxAppxFileNm;

    // 랭킹점수
    private float score;

    static public SearchVo getSearchResult(Search search, String COLLECTION) {

        List<Notice> list = new ArrayList<>();

        // 전체건수, 결과건수 출력
        int totalCount = search.w3GetResultTotalCount(COLLECTION);
        int resultCount = search.w3GetResultCount(COLLECTION);

        for (int i = 0; i < resultCount; i++) {

            // 기본 검색결과 객체 생성
            String bbNo = search.w3GetField(COLLECTION, "BB_NO", i);
            String rn = search.w3GetField(COLLECTION, "RN", i);
            String oppbYN = search.w3GetField(COLLECTION, "OPPB_YN", i);
            String strtDt = search.w3GetField(COLLECTION, "STRT_DT", i);
            String endDt = search.w3GetField(COLLECTION, "END_DT", i);
            String title = search.w3GetField(COLLECTION, "TITLE", i)
                                 .replaceAll("<!HS>", "<em class=\"keyword\">")
                                 .replaceAll("<!HE>", "</em>");
            String content = search.w3GetField(COLLECTION, "CONTENT", i)
                                   .replaceAll("<!HS>", "<em class=\"keyword\">")
                                   .replaceAll("<!HE>", "</em>");
            String uptnDispYN = search.w3GetField(COLLECTION, "UPTN_DISP_YN", i);
            String regDt = search.w3GetField(COLLECTION, "REG_DT", i);
            String appxAppxFileNo = search.w3GetField(COLLECTION, "APPX_APPX_FILE_NO", i);
            String appxRegSeq = search.w3GetField(COLLECTION, "APPX_REG_SEQ", i);
            String appxAppxFileKndCd = search.w3GetField(COLLECTION, "APPX_APPX_FILE_KND_CD", i);
            String appxUpldFilePath = search.w3GetField(COLLECTION, "APPX_UPLD_FILE_PATH", i);
            String appxAppxFileNm = search.w3GetField(COLLECTION, "APPX_UPLD_FILE_NM", i);
            float score = search.w3GetRank(COLLECTION, i);

            Notice vo = Notice.builder()
                              .score(score)
                              .bbNo(bbNo)
                              .rn(rn)
                              .oppbYN(oppbYN)
                              .strtDt(strtDt)
                              .endDt(endDt)
                              .title(title)
                              .content(content)
                              .uptnDispYN(uptnDispYN)
                              .regDt(regDt)
                              .appxAppxFileNo(appxAppxFileNo)
                              .appxRegSeq(appxRegSeq)
                              .appxAppxFileKndCd(appxAppxFileKndCd)
                              .appxUpldFilePath(appxUpldFilePath)
                              .appxAppxFileNm(appxAppxFileNm)
                              .build();

            list.add(vo);

        }

        return SearchVo.<Notice>builder()
                       .collection(COLLECTION)
                       .totalCount(totalCount)
                       .count(resultCount)
                       .result(list)
                       .build();
    }

}
