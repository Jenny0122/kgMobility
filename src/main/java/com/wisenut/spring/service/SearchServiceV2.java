package com.wisenut.spring.service;

import QueryAPI7.Search;
import com.wisenut.spring.dto.TotalSearchRequestDTO;
import com.wisenut.spring.dto.TotalSearchResponseDTO;
import com.wisenut.spring.vo.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SearchServiceV2 {

    final int QUERY_LOG = 1;

    final String ENCODE_VALUE = "UTF-8";

    final int SERVER_PORT = 7000;

    final int SERVER_TIMEOUT = 10 * 1000;

    @Value("${wise.sf1.serverIp}")
    String SERVER_IP;

    public TotalSearchResponseDTO run(String language, TotalSearchRequestDTO requestDTO) {

        TotalSearchResponseDTO dto = new TotalSearchResponseDTO();

        String query = requestDTO.getQuery()
                                 .trim();
        String COLLECTION = requestDTO.getCollection()
                                      .trim();
        String SORT_FIELD = requestDTO.getSortField().trim() + "/" + requestDTO.getSortDirection().trim();
        int RESULT_COUNT = requestDTO.getCount(); // 한번에 출력되는 검색 건수
        int PAGE_START = requestDTO.getPageStart(); // 검색결과 페이지


        dto.setQuery(query);

        Search search = new Search();
        int ret = 0;

        // common query 설정
        ret = search.w3SetCodePage(ENCODE_VALUE);
        ret = search.w3SetQueryLog(QUERY_LOG);
        ret = search.w3SetCommonQuery(query);

        SearchVo purchaseSearch = null;
        SearchVo modelSearch = null;
        SearchVo newsSearch = null;
        SearchVo eventSearch = null;
        SearchVo noticeSearch = null;
        SearchVo customerSearch = null;
        SearchVo brandSearch = null;
        SearchVo aboutSearch = null;
        SearchVo applySearch = null;
        SearchVo auditSearch = null;


        String PURCHASE = "purchase" + language;
        String MODEL = "model" + language;
        String NEWS = "news" + language;
        String EVENT = "event" + language;
        String NOTICE = "notice" + language;
        String CUSTOMER = "customer" + language;
        String BRAND = "brand" + language;
        String ABOUT = "about" + language;
        String APPLY = "apply" + language;
        String AUDIT = "audit" + language;

        switch (COLLECTION) {
            case "ALL":
                purchaseSearch = Purchase.getSearchResult(this.searchPurchase(search, PURCHASE, SORT_FIELD, RESULT_COUNT, PAGE_START), PURCHASE);
                dto.setPurchase(purchaseSearch);

                modelSearch = Model.getSearchResult(this.searchModel(search, MODEL, SORT_FIELD, RESULT_COUNT, PAGE_START), MODEL);
                dto.setModel(modelSearch);

                newsSearch = News.getSearchResult(this.searchNews(search, NEWS, SORT_FIELD, RESULT_COUNT, PAGE_START), NEWS);
                dto.setNews(newsSearch);

                eventSearch = Event.getSearchResult(this.searchEvent(search, EVENT, SORT_FIELD, RESULT_COUNT, PAGE_START), EVENT);
                dto.setEvent(eventSearch);

                noticeSearch = Notice.getSearchResult(this.searchNotice(search, NOTICE, SORT_FIELD, RESULT_COUNT, PAGE_START), NOTICE);
                dto.setNotice(noticeSearch);

                customerSearch = Html.getSearchResult(this.searchHtml(search, CUSTOMER, SORT_FIELD, RESULT_COUNT, PAGE_START), CUSTOMER);
                dto.setCustomer(customerSearch);

                brandSearch = Html.getSearchResult(this.searchHtml(search, BRAND, SORT_FIELD, RESULT_COUNT, PAGE_START), BRAND);
                dto.setBrand(brandSearch);

                aboutSearch = Html.getSearchResult(this.searchHtml(search, ABOUT, SORT_FIELD, RESULT_COUNT, PAGE_START), ABOUT);
                dto.setAbout(aboutSearch);

                applySearch = Html.getSearchResult(this.searchHtml(search, APPLY, SORT_FIELD, RESULT_COUNT, PAGE_START), APPLY);
                dto.setApply(applySearch);

                auditSearch = Html.getSearchResult(this.searchHtml(search, AUDIT, SORT_FIELD, RESULT_COUNT, PAGE_START), AUDIT);
                dto.setAudit(auditSearch);

                break;

            case "pressEvent":
                newsSearch = News.getSearchResult(this.searchNews(search, NEWS, SORT_FIELD, RESULT_COUNT, PAGE_START), NEWS);
                dto.setNews(newsSearch);

                eventSearch = Event.getSearchResult(this.searchEvent(search, EVENT, SORT_FIELD, RESULT_COUNT, PAGE_START), EVENT);
                dto.setEvent(eventSearch);

                noticeSearch = Notice.getSearchResult(this.searchNotice(search, NOTICE, SORT_FIELD, RESULT_COUNT, PAGE_START), NOTICE);
                dto.setNotice(noticeSearch);

                break;

            case "purchase":
                purchaseSearch = Purchase.getSearchResult(this.searchPurchase(search, PURCHASE, SORT_FIELD, RESULT_COUNT, PAGE_START), PURCHASE);
                dto.setPurchase(purchaseSearch);
                break;

            case "model":
                modelSearch = Model.getSearchResult(this.searchModel(search, MODEL, SORT_FIELD, RESULT_COUNT, PAGE_START), MODEL);
                dto.setModel(modelSearch);
                break;

            case "news":
                newsSearch = News.getSearchResult(this.searchNews(search, NEWS, SORT_FIELD, RESULT_COUNT, PAGE_START), NEWS);
                dto.setNews(newsSearch);
                break;

            case "event":
                eventSearch = Event.getSearchResult(this.searchEvent(search, EVENT, SORT_FIELD, RESULT_COUNT, PAGE_START), EVENT);
                dto.setEvent(eventSearch);
                break;

            case "notice":
                noticeSearch = Notice.getSearchResult(this.searchNotice(search, NOTICE, SORT_FIELD, RESULT_COUNT, PAGE_START), NOTICE);
                dto.setNotice(noticeSearch);
                break;

            case "customer":
                customerSearch = Html.getSearchResult(this.searchHtml(search, CUSTOMER, SORT_FIELD, RESULT_COUNT, PAGE_START), CUSTOMER);
                dto.setCustomer(customerSearch);
                break;

            case "brand":
                brandSearch = Html.getSearchResult(this.searchHtml(search, BRAND, SORT_FIELD, RESULT_COUNT, PAGE_START), BRAND);
                dto.setBrand(brandSearch);
                break;

            case "about":
                aboutSearch = Html.getSearchResult(this.searchHtml(search, ABOUT, SORT_FIELD, RESULT_COUNT, PAGE_START), ABOUT);
                dto.setAbout(aboutSearch);
                break;

            case "apply":
                applySearch = Html.getSearchResult(this.searchHtml(search, APPLY, SORT_FIELD, RESULT_COUNT, PAGE_START), APPLY);
                dto.setApply(applySearch);
                break;

            case "audit":
                auditSearch = Html.getSearchResult(this.searchHtml(search, AUDIT, SORT_FIELD, RESULT_COUNT, PAGE_START), AUDIT);
                dto.setAudit(auditSearch);
                break;

            default:
                break;
        }

        return dto;
    }


    private Search searchPurchase(Search search, String COLLECTION, String SORT_FIELD, int RESULT_COUNT, int PAGE_START) {

        int ret = 0;
        List<String> SEARCH_FIELD_LIST = null;
        String SEARCH_FIELD = null;
        String DOCUMENT_FIELD = null;

        SEARCH_FIELD_LIST = getPurchaseSearchFieldList();
        SEARCH_FIELD = String.join(",", SEARCH_FIELD_LIST);
        DOCUMENT_FIELD = getPurchaseDocumentFieldList();

        // collection, 검색 필드, 출력 필드 설정
        ret = search.w3AddCollection(COLLECTION);
        ret = search.w3SetPageInfo(COLLECTION, PAGE_START, RESULT_COUNT);
        ret = search.w3SetSortField(COLLECTION, SORT_FIELD);
        ret = search.w3SetSimilarity(COLLECTION, "basic", "BM25", 100);
        ret = search.w3SetQueryAnalyzer(COLLECTION, 1, 1, 1, 0);
        ret = search.w3SetRecentQuery(COLLECTION, 1);

        ret = search.w3SetSearchField(COLLECTION, SEARCH_FIELD);
        ret = search.w3SetDocumentField(COLLECTION, DOCUMENT_FIELD);
        for (String field : SEARCH_FIELD_LIST)
            ret = search.w3AddHighlight(COLLECTION, field);


        // request
        ret = search.w3ConnectServer(SERVER_IP, SERVER_PORT, SERVER_TIMEOUT);
        ret = search.w3ReceiveSearchQueryResult();

        // check error
        if (!search.w3GetErrorInfo()
                   .contentEquals("no error")) {
            log.debug("purchase 검색 오류 로그 : {}", search.w3GetErrorInfo());
            return null;
        }

        return search;
    }

    private Search searchModel(Search search, String COLLECTION, String SORT_FIELD, int RESULT_COUNT, int PAGE_START) {

        int ret = 0;
        List<String> SEARCH_FIELD_LIST = null;
        String SEARCH_FIELD = null;
        String DOCUMENT_FIELD = null;

        SEARCH_FIELD_LIST = getModelSearchFieldList();
        SEARCH_FIELD = String.join(",", SEARCH_FIELD_LIST);
        DOCUMENT_FIELD = getModelDocumentFieldList();

        // collection, 검색 필드, 출력 필드 설정
        ret = search.w3AddCollection(COLLECTION);
        ret = search.w3SetPageInfo(COLLECTION, PAGE_START, RESULT_COUNT);
        ret = search.w3SetSortField(COLLECTION, SORT_FIELD);
        ret = search.w3SetSimilarity(COLLECTION, "basic", "BM25", 100);
        ret = search.w3SetQueryAnalyzer(COLLECTION, 1, 1, 1, 0);
        ret = search.w3SetRecentQuery(COLLECTION, 1);


        ret = search.w3SetSearchField(COLLECTION, SEARCH_FIELD);
        ret = search.w3SetDocumentField(COLLECTION, DOCUMENT_FIELD);
        for (String field : SEARCH_FIELD_LIST)
            ret = search.w3AddHighlight(COLLECTION, field);


        // request
        ret = search.w3ConnectServer(SERVER_IP, SERVER_PORT, SERVER_TIMEOUT);
        ret = search.w3ReceiveSearchQueryResult();

        // check error
        if (!search.w3GetErrorInfo()
                   .contentEquals("no error")) {
            log.debug("purchase 검색 오류 로그 : {}", search.w3GetErrorInfo());
            return null;
        }

        return search;
    }

    private Search searchEvent(Search search, String COLLECTION, String SORT_FIELD, int RESULT_COUNT, int PAGE_START) {

        int ret = 0;
        List<String> SEARCH_FIELD_LIST = null;
        String SEARCH_FIELD = null;
        String DOCUMENT_FIELD = null;

        SEARCH_FIELD_LIST = getPressEventSearchFieldList();
        SEARCH_FIELD = String.join(",", SEARCH_FIELD_LIST);
        DOCUMENT_FIELD = getEventDocumentFieldList();

        // collection, 검색 필드, 출력 필드 설정
        ret = search.w3AddCollection(COLLECTION);
        ret = search.w3SetPageInfo(COLLECTION, PAGE_START, RESULT_COUNT);
        ret = search.w3SetSortField(COLLECTION, SORT_FIELD);
        ret = search.w3SetSimilarity(COLLECTION, "basic", "BM25", 100);
        ret = search.w3SetQueryAnalyzer(COLLECTION, 1, 1, 1, 0);
        ret = search.w3SetRecentQuery(COLLECTION, 1);


        ret = search.w3SetSearchField(COLLECTION, SEARCH_FIELD);
        ret = search.w3SetDocumentField(COLLECTION, DOCUMENT_FIELD);
        for (String field : SEARCH_FIELD_LIST)
            ret = search.w3AddHighlight(COLLECTION, field);


        // request
        ret = search.w3ConnectServer(SERVER_IP, SERVER_PORT, SERVER_TIMEOUT);
        ret = search.w3ReceiveSearchQueryResult();

        // check error
        if (!search.w3GetErrorInfo()
                   .contentEquals("no error")) {
            log.debug("purchase 검색 오류 로그 : {}", search.w3GetErrorInfo());
            return null;
        }

        return search;
    }

    private Search searchNews(Search search, String COLLECTION, String SORT_FIELD, int RESULT_COUNT, int PAGE_START) {

        int ret = 0;
        List<String> SEARCH_FIELD_LIST = null;
        String SEARCH_FIELD = null;
        String DOCUMENT_FIELD = null;

        SEARCH_FIELD_LIST = getPressEventSearchFieldList();
        SEARCH_FIELD = String.join(",", SEARCH_FIELD_LIST);
        DOCUMENT_FIELD = getNewsDocumentFieldList();

        // collection, 검색 필드, 출력 필드 설정
        ret = search.w3AddCollection(COLLECTION);
        ret = search.w3SetPageInfo(COLLECTION, PAGE_START, RESULT_COUNT);
        ret = search.w3SetSortField(COLLECTION, SORT_FIELD);
        ret = search.w3SetSimilarity(COLLECTION, "basic", "BM25", 100);
        ret = search.w3SetQueryAnalyzer(COLLECTION, 1, 1, 1, 0);
        ret = search.w3SetRecentQuery(COLLECTION, 1);


        ret = search.w3SetSearchField(COLLECTION, SEARCH_FIELD);
        ret = search.w3SetDocumentField(COLLECTION, DOCUMENT_FIELD);
        for (String field : SEARCH_FIELD_LIST)
            ret = search.w3AddHighlight(COLLECTION, field);


        // request
        ret = search.w3ConnectServer(SERVER_IP, SERVER_PORT, SERVER_TIMEOUT);
        ret = search.w3ReceiveSearchQueryResult();

        // check error
        if (!search.w3GetErrorInfo()
                   .contentEquals("no error")) {
            log.debug("purchase 검색 오류 로그 : {}", search.w3GetErrorInfo());
            return null;
        }

        return search;
    }

    private Search searchNotice(Search search, String COLLECTION, String SORT_FIELD, int RESULT_COUNT, int PAGE_START) {

        int ret = 0;
        List<String> SEARCH_FIELD_LIST = null;
        String SEARCH_FIELD = null;
        String DOCUMENT_FIELD = null;

        SEARCH_FIELD_LIST = getPressEventSearchFieldList();
        SEARCH_FIELD = String.join(",", SEARCH_FIELD_LIST);
        DOCUMENT_FIELD = getNoticeDocumentFieldList();

        // collection, 검색 필드, 출력 필드 설정
        ret = search.w3AddCollection(COLLECTION);
        ret = search.w3SetPageInfo(COLLECTION, PAGE_START, RESULT_COUNT);
        ret = search.w3SetSortField(COLLECTION, SORT_FIELD);
        ret = search.w3SetSimilarity(COLLECTION, "basic", "BM25", 100);
        ret = search.w3SetQueryAnalyzer(COLLECTION, 1, 1, 1, 0);
        ret = search.w3SetRecentQuery(COLLECTION, 1);

        ret = search.w3SetSearchField(COLLECTION, SEARCH_FIELD);
        ret = search.w3SetDocumentField(COLLECTION, DOCUMENT_FIELD);
        for (String field : SEARCH_FIELD_LIST)
            ret = search.w3AddHighlight(COLLECTION, field);

        // request
        ret = search.w3ConnectServer(SERVER_IP, SERVER_PORT, SERVER_TIMEOUT);
        ret = search.w3ReceiveSearchQueryResult();

        // check error
        if (!search.w3GetErrorInfo()
                   .contentEquals("no error")) {
            log.debug("purchase 검색 오류 로그 : {}", search.w3GetErrorInfo());
            return null;
        }

        return search;
    }

    private Search searchHtml(Search search, String COLLECTION, String SORT_FIELD, int RESULT_COUNT, int PAGE_START) {
        int ret = 0;
        List<String> SEARCH_FIELD_LIST = null;
        String SEARCH_FIELD = null;
        String DOCUMENT_FIELD = null;

        SEARCH_FIELD_LIST = getHtmlSearchFieldList();
        SEARCH_FIELD = String.join(",", SEARCH_FIELD_LIST);
        DOCUMENT_FIELD = getHtmlDocumentFieldList();

        // collection, 검색 필드, 출력 필드 설정
        ret = search.w3AddCollection(COLLECTION);
        ret = search.w3SetPageInfo(COLLECTION, PAGE_START, RESULT_COUNT);
        ret = search.w3SetSortField(COLLECTION, SORT_FIELD);
        ret = search.w3SetSimilarity(COLLECTION, "basic", "BM25", 100);
        ret = search.w3SetQueryAnalyzer(COLLECTION, 1, 1, 1, 0);
        ret = search.w3SetRecentQuery(COLLECTION, 1);

        ret = search.w3SetSearchField(COLLECTION, SEARCH_FIELD);
        ret = search.w3SetDocumentField(COLLECTION, DOCUMENT_FIELD);
        for (String field : SEARCH_FIELD_LIST)
            ret = search.w3AddHighlight(COLLECTION, field);

        // request
        ret = search.w3ConnectServer(SERVER_IP, SERVER_PORT, SERVER_TIMEOUT);
        ret = search.w3ReceiveSearchQueryResult();

        // check error
        if (!search.w3GetErrorInfo()
                   .contentEquals("no error")) {
            log.debug("purchase 검색 오류 로그 : {}", search.w3GetErrorInfo());
            return null;
        }

        return search;
    }

    /**
     * purchase search field list를 정의
     *
     * @return
     */
    private List<String> getPurchaseSearchFieldList() {
        List<String> list = new ArrayList<>();

        list.add("M_CAT_NM");
        list.add("S_CAT_NM");
        list.add("MODEL_NAME");
        list.add("MODEL_CD");
        list.add("MODEL_INFO");
        list.add("ENGINE_INFO");
        list.add("BENEFITS_MONTH");
        list.add("SPECIAL_BENEFITS");

        return list;
    }

    /**
     * purchase document field list를 정의
     *
     * @return
     */
    private String getPurchaseDocumentFieldList() {

        List<String> list = new ArrayList<>();

        list.add("DOCID");
        list.add("DATE");
        list.add("DISP_CAT_NO");
        list.add("LRG_CAT_NO");
        list.add("L_CAT_NM");
        list.add("MID_CAT_NO");
        list.add("M_CAT_NM");
        list.add("SML_CAT_NO");
        list.add("S_CAT_NM");
        list.add("UPR_DISP_CAT_NO");
        list.add("DTL_CAT_NO");
        list.add("MODEL_NAME");
        list.add("MODEL_CD");
        list.add("MODEL_AMT");
        list.add("CAR_TYPE");
        list.add("OD_URL");
        list.add("MODEL_INFO");
        list.add("ENGINE_INFO");
        list.add("MODEL_IMG_ADDR");
        list.add("MODEL_MAIN_IMG_ADDR");
        list.add("MODEL_THMN_IMG_ADDR");
        list.add("MKTG_TXT");
        list.add("BENEFITS_MONTH");
        list.add("SPECIAL_BENEFITS");
        list.add("CATALOG_FILE_NAME");
        list.add("CATALOG_ADDR");
        list.add("E_CATALOG_LNK_URL_ADDR");
        list.add("PRICE_LIST_FILE_NAME");
        list.add("PRICE_LIST_ADDR");
        list.add("ACCESSORIES_FILE_NAME");
        list.add("ACCESSORIES_ADDR");
        list.add("BANNER_PC_IMG_ADDR");
        list.add("BANNER_MO_IMG_ADDR");
        list.add("DISP_SEQ");
        list.add("alias");

        return String.join(",", list);
    }

    /**
     * model search field list를 정의
     *
     * @return
     */
    private List<String> getModelSearchFieldList() {
        List<String> list = new ArrayList<>();

        list.add("MODEL_NAME");
        list.add("MODEL_CD");

        return list;
    }

    /**
     * model document field list를 정의
     *
     * @return
     */
    private String getModelDocumentFieldList() {

        List<String> list = new ArrayList<>();
        list.add("DOCID");
        list.add("DATE");
        list.add("DISP_CAT_NO");
        list.add("LRG_CAT_NO");
        list.add("L_CAT_NM");
        list.add("MID_CAT_NO");
        list.add("M_CAT_NM");
        list.add("UPR_DISP_CAT_NO");
        list.add("SML_CAT_NO");
        list.add("MODEL_NAME");
        list.add("MODEL_CD");
        list.add("MODEL_AMT");
        list.add("CAR_TYPE");
        list.add("MODEL_CAT_IMG_ADDR");
        list.add("MODEL_DISP_FLG");
        list.add("CARINFO_DISP_CAT_NO");
        list.add("ELECCAR_YN");
        list.add("DISP_STAT_CD");
        list.add("TAXBEN_YN");
        list.add("RSV_1_ATTR");
        list.add("RSV_2_ATTR");
        list.add("UPR_DISP_SEQ");
        list.add("MODEL_DISP_SEQ");
        list.add("alias");
        return String.join(",", list);
    }

    /**
     * news,event,notice search field list를 정의
     *
     * @return
     */
    private List<String> getPressEventSearchFieldList() {
        List<String> list = new ArrayList<>();
        list.add("TITLE");
        list.add("CONTENT");
        return list;
    }

    /**
     * news document field list를 정의
     *
     * @return
     */
    private String getNewsDocumentFieldList() {
        List<String> list = new ArrayList<>();

        list.add("DOCID");
        list.add("DATE");
        list.add("BB_NO");
        list.add("RN");
        list.add("OPPB_YN");
        list.add("STRT_DT");
        list.add("END_DT");
        list.add("TITLE");
        list.add("CONTENT");
        list.add("UPTN_DISP_YN");
        list.add("REG_DT");
        list.add("SITE_CD");
        list.add("APPX_APPX_FILE_NO");
        list.add("APPX_REG_SEQ");
        list.add("APPX_UPLD_FILE_PATH");
        list.add("APPX_UPLD_FILE_NM");
        list.add("alias");
        return String.join(",", list);
    }

    /**
     * event document field list를 정의
     *
     * @return
     */
    private String getEventDocumentFieldList() {
        List<String> list = new ArrayList<>();
        list.add("DOCID");
        list.add("DATE");
        list.add("BB_NO");
        list.add("OPPB_YN");
        list.add("TITLE");
        list.add("CONTENT");
        list.add("BNNR_IMG_URL");
        list.add("STRT_DT");
        list.add("END_DT");
        list.add("REG_DT");
        list.add("APPX_APPX_FILE_NO");
        list.add("APPX_UPLD_FILE_PATH");
        list.add("APPX_UPLD_FILE_NM");
        list.add("STATUS_CD");
        list.add("alias");
        return String.join(",", list);
    }

    /**
     * notice document field list를 정의
     *
     * @return
     */
    private String getNoticeDocumentFieldList() {
        List<String> list = new ArrayList<>();
        list.add("DOCID");
        list.add("DATE");
        list.add("BB_NO");
        list.add("RN");
        list.add("OPPB_YN");
        list.add("STRT_DT");
        list.add("END_DT");
        list.add("TITLE");
        list.add("CONTENT");
        list.add("UPTN_DISP_YN");
        list.add("REG_DT");
        list.add("APPX_APPX_FILE_NO");
        list.add("APPX_REG_SEQ");
        list.add("APPX_APPX_FILE_KND_CD");
        list.add("APPX_UPLD_FILE_PATH");
        list.add("APPX_UPLD_FILE_NM");
        list.add("alias");
        return String.join(",", list);
    }

    /**
     * 정적페이지 search field list를 정의
     *
     * @return
     */
    private List<String> getHtmlSearchFieldList() {
        List<String> list = new ArrayList<>();
        list.add("TAB_NM");
        list.add("MENU_PATH_NM");
        list.add("TXT_CONT");
        return list;
    }

    /**
     * 정적페이지 document field list를 정의
     *
     * @return
     */
    private String getHtmlDocumentFieldList() {
        List<String> list = new ArrayList<>();
        list.add("DOCID");
        list.add("DATE");
        list.add("SEARCH_STATIC_NO");
        list.add("TAB_CD");
        list.add("TAB_NM");
        list.add("MENU_ID");
        list.add("MENU_PATH_NM");
        list.add("MENU_PATH_URL");
        list.add("MENU_DEPTH1_NM");
        list.add("MENU_DEPTH2_NM");
        list.add("MENU_DEPTH3_NM");
        list.add("MENU_DEPTH4_NM");
        list.add("MENU_DEPTH5_NM");
        list.add("MENU_DEPTH6_NM");
        list.add("MENU_DEPTH7_NM");
        list.add("PROG_DEPTH1_NM");
        list.add("PROG_DEPTH2_NM");
        list.add("PROG_DEPTH3_NM");
        list.add("PROG_DEPTH4_NM");
        list.add("PROG_DEPTH5_NM");
        list.add("PROG_FILE_NM");
        list.add("TXT_CONT");
        list.add("SYS_REG_DTIME");
        list.add("SYS_REGR_ID");
        list.add("SYS_MOD_DTIME");
        list.add("SYS_MODR_ID");
        list.add("alias");
        return String.join(",", list);
    }

}

