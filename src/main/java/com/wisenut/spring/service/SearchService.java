package com.wisenut.spring.service;

import QueryAPI7.Search;
import com.wisenut.spring.MissingArgumentException;
import com.wisenut.spring.vo.SearchVo;
import com.wisenut.spring.vo.purchaseVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class SearchService {

    final int QUERY_LOG = 1;

    final String ENCODE_VALUE = "UTF-8";

    final int SERVER_PORT = 7000;

    final int SERVER_TIMEOUT = 10 * 1000;

    @Value("${wise.sf1.serverIp}")
    String SERVER_IP;


    public SearchVo searchPurchaseTotalList(Map<String, String> params) {

        List<purchaseVo> list = new ArrayList<>();

        String query = params.getOrDefault("query", "");

        String COLLECTION = "";
        if (params.containsKey("collection")) {
            COLLECTION = params.get("collection");
            if (COLLECTION.contentEquals("ALL"))
                COLLECTION = "page";
        } else {
            throw new MissingArgumentException("collection은 '필수'값 입니다.");
        }

        int RESULT_COUNT = Integer.parseInt(params.getOrDefault("count", String.valueOf(10))); // 한번에 출력되는 검색 건수
        int PAGE_START = Integer.parseInt(params.getOrDefault("pageStart", String.valueOf(0)));

        List<String> SEARCH_FIELD_LIST = new ArrayList<>();
        SEARCH_FIELD_LIST.add("M_CAT_NM");
        SEARCH_FIELD_LIST.add("S_CAT_NM");
        SEARCH_FIELD_LIST.add("MODEL_NAME");
        SEARCH_FIELD_LIST.add("MODEL_CD");
        SEARCH_FIELD_LIST.add("MODEL_INFO");
        SEARCH_FIELD_LIST.add("ENGINE_INFO");
        SEARCH_FIELD_LIST.add("BENEFITS_MONTH");
        SEARCH_FIELD_LIST.add("SPECIAL_BENEFITS");
        final String SEARCH_FIELD = String.join(",", SEARCH_FIELD_LIST);

        String DOCUMENT_FIELD = "DOCID,DATE,DISP_CAT_NO,LRG_CAT_NO,L_CAT_NM,MID_CAT_NO,M_CAT_NM,SML_CAT_NO,S_CAT_NM,UPR_DISP_CAT_NO,DTL_CAT_NO,MODEL_NAME,MODEL_CD,MODEL_AMT,CAR_TYPE,OD_URL,MODEL_INFO,ENGINE_INFO,MODEL_IMG_ADDR,MODEL_MAIN_IMG_ADDR,MODEL_THMN_IMG_ADDR,MKTG_TXT,BENEFITS_MONTH,SPECIAL_BENEFITS,CATALOG_FILE_NAME,E_CATALOG_LNK_URL_ADDR,PRICE_LIST_FILE_NAME,PRICE_LIST_ADDR,ACCESSORIES_FILE_NAME,ACCESSORIES_ADDR,BANNER_PC_IMG_ADDR,BANNER_MO_IMG_ADDR,DISP_SEQ,alias";

        // 정렬필드
        String SORT_FIELD = "";
        if (params.containsKey("sortColumnIndex") && params.containsKey("sortDirection"))
            SORT_FIELD = params.get("sortColumnIndex") + "/" + params.get("sortDirection");
        else
            SORT_FIELD = "SCORE/DESC";

        // create object
        Search search = new Search();
        int ret = 0;

        // common query 설정
        ret = search.w3SetCodePage(ENCODE_VALUE);
        ret = search.w3SetQueryLog(QUERY_LOG);
        ret = search.w3SetCommonQuery(query);

        // collection, 검색 필드, 출력 필드 설정
        ret = search.w3AddCollection(COLLECTION);
        ret = search.w3SetPageInfo(COLLECTION, PAGE_START, RESULT_COUNT);
        ret = search.w3SetSortField(COLLECTION, SORT_FIELD);
        ret = search.w3SetSearchField(COLLECTION, SEARCH_FIELD);
        ret = search.w3SetDocumentField(COLLECTION, DOCUMENT_FIELD);
        ret = search.w3AddHighlight(COLLECTION, SEARCH_FIELD);
        ret = search.w3SetQueryAnalyzer(COLLECTION, 1, 1,1, 0);

        // request
        ret = search.w3ConnectServer(SERVER_IP, SERVER_PORT, SERVER_TIMEOUT);
        ret = search.w3ReceiveSearchQueryResult();

        // check error
        if (!search.w3GetErrorInfo().contentEquals("")) {
            log.debug("검색 오류 로그 : {}", search.w3GetErrorInfo());
            return null;
        }

        // 전체건수, 결과건수 출력
        int totalCount = search.w3GetResultTotalCount(COLLECTION);
        int resultCount = search.w3GetResultCount(COLLECTION);


        return SearchVo.builder()
                .collection(COLLECTION)
                .totalCount(totalCount)
                .count(resultCount)
                .result(list)
                .build();
    }

    public SearchVo searchModelTotalList(Map<String, String> params) {

        return null;
    }

    public SearchVo searchNewsTotalList(Map<String, String> params) {return null;}

    public SearchVo searchEventTotalList(Map<String, String> params) {return null;}

    public SearchVo searchNoticeTotalList(Map<String, String> params) {return null;}



    public void test(String language) {

        testA("kor");
        testA("eng");
    }

    public void testA(String language) {

        testB(language, "collection1");
        testB(language, "collection2");
        testB(language, "collection3");
        testB(language, "collection4");
        testB(language, "collection5");
    }

    public void testB(String langauge, String collection) {
        String language;

    }
}

