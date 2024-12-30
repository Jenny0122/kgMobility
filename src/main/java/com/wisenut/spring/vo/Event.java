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
// 이벤트 필드 정보
public class Event {

    //
    private String bbNo;

    //
    private String oppbYN;

    // 제목
    private String title;

    // 내용
    private String content;

    // 배너이미지 URL
    private String bnnrImgUrl;

    // 시작날짜
    private String strtDt;

    // 종료날짜
    private String endDt;

    // 등록날짜
    private String regDt;

    // 첨부파일번호
    private String appxAppxFileNo;

    // 업로드파일경로
    private String appxUpldFilePath;

    // 업로드파일명
    private String appxAppxFileNm;

    // 진행여부 code
    private String statusCd;

    // 랭킹점수
    private float score;

    public static SearchVo getSearchResult(Search search, String COLLECTION) {
        List<Event> list = new ArrayList<>();

        // 전체건수, 결과건수 출력
        int totalCount = search.w3GetResultTotalCount(COLLECTION);
        int resultCount = search.w3GetResultCount(COLLECTION);

        for (int i = 0; i < resultCount; i++) {

            // 기본 검색결과 객체 생성
            String bbNo = search.w3GetField(COLLECTION, "BB_NO", i);
            String oppbYN = search.w3GetField(COLLECTION, "OPPB_YN", i);
            String title = search.w3GetField(COLLECTION, "TITLE", i)
                                 .replaceAll("<!HS>", "<em class=\"keyword\">")
                                 .replaceAll("<!HE>", "</em>");
            String content = search.w3GetField(COLLECTION, "CONTENT", i)
                                   .replaceAll("<!HS>", "<em class=\"keyword\">")
                                   .replaceAll("<!HE>", "</em>");
            String bnnrImgUrl = search.w3GetField(COLLECTION, "BNNR_IMG_URL", i);
            String strtDt = search.w3GetField(COLLECTION, "STRT_DT", i);
            String endDt = search.w3GetField(COLLECTION, "END_DT", i);
            String regDt = search.w3GetField(COLLECTION, "REG_DT", i);
            String appxAppxFileNo = search.w3GetField(COLLECTION, "APPX_APPX_FILE_NO", i);
            String appxUpldFilePath = search.w3GetField(COLLECTION, "APPX_UPLD_FILE_PATH", i);
            String appxAppxFileNm = search.w3GetField(COLLECTION, "APPX_UPLD_FILE_NM", i);
            String statusCd = search.w3GetField(COLLECTION, "SATUS_CD", i);
            float score = search.w3GetRank(COLLECTION, i);

            Event vo = Event.builder()
                            .score(score)
                            .bbNo(bbNo)
                            .oppbYN(oppbYN)
                            .title(title)
                            .content(content)
                            .bnnrImgUrl(bnnrImgUrl)
                            .strtDt(strtDt)
                            .endDt(endDt)
                            .regDt(regDt)
                            .appxAppxFileNo(appxAppxFileNo)
                            .appxUpldFilePath(appxUpldFilePath)
                            .appxAppxFileNm(appxAppxFileNm)
                            .statusCd(statusCd)
                            .build();

            list.add(vo);
        }

        return SearchVo.<Event>builder()
                       .collection(COLLECTION)
                       .totalCount(totalCount)
                       .count(resultCount)
                       .result(list)
                       .build();
    }
}
