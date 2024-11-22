package com.wisenut.spring.service;

import QueryAPI7.Search;
import com.wisenut.spring.MissingArgumentException;
import com.wisenut.spring.vo.*;
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

        List<PurchaseVo> list = new ArrayList<>();

        String query = params.getOrDefault("query", "");

        String COLLECTION = "";
        if (params.containsKey("collection")) {
            COLLECTION = params.get("collection");
            if (COLLECTION.contentEquals("ALL"))
                COLLECTION = "purchase";
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
        if (params.containsKey("sortField") && params.containsKey("sortDirection"))
            SORT_FIELD = params.get("sortField") + "/" + params.get("sortDirection");
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
        for (String field : SEARCH_FIELD_LIST) {
            ret = search.w3AddHighlight(COLLECTION, field);
        }
        ret = search.w3SetSimilarity(COLLECTION, "basic", "BM25", 100);
        ret = search.w3SetQueryAnalyzer(COLLECTION, 1, 1,1, 0);
        ret = search.w3SetRecentQuery(COLLECTION, 1);

        // request
        ret = search.w3ConnectServer(SERVER_IP, SERVER_PORT, SERVER_TIMEOUT);
        ret = search.w3ReceiveSearchQueryResult();

        // check error
        if (!search.w3GetErrorInfo().contentEquals("no error")) {
            log.debug("purchase 검색 오류 로그 : {}", search.w3GetErrorInfo());
            return null;
        }

        // 전체건수, 결과건수 출력
        int totalCount = search.w3GetResultTotalCount(COLLECTION);
        int resultCount = search.w3GetResultCount(COLLECTION);

        log.debug("purchase 검색 결과 : {}건 / 전체 건수 : {}건", resultCount, totalCount);

        for (int i = 0; i < resultCount; i++) {

            // 기본 검색결과 객체 생성
            String dispCatNo = search.w3GetField(COLLECTION, "DISP_CAT_NO", i);
            String lrgCatNo = search.w3GetField(COLLECTION, "LRG_CAT_NO", i);
            String lCatNm = search.w3GetField(COLLECTION, "L_CAT_NM", i);
            String midCatNo = search.w3GetField(COLLECTION, "MID_CAT_NO", i);
            String mCatNm = search.w3GetField(COLLECTION, "M_CAT_NM", i)
                    .replaceAll("<!HS>", "<b>")
                    .replaceAll("<!HE>", "</b>");
            String smlCatNo = search.w3GetField(COLLECTION, "SML_CAT_NO", i);
            String sCatNm = search.w3GetField(COLLECTION, "S_CAT_NM", i)
                    .replaceAll("<!HS>", "<b>")
                    .replaceAll("<!HE>", "</b>");
            String uprDispCatNo = search.w3GetField(COLLECTION, "UPR_DISP_CAT_NO", i);
            String dtlCatNo = search.w3GetField(COLLECTION, "DTL_CAT_NO", i);
            String modelName = search.w3GetField(COLLECTION, "MODEL_NAME", i)
                    .replaceAll("<!HS>", "<b>")
                    .replaceAll("<!HE>", "</b>");
            String modelCd = search.w3GetField(COLLECTION, "MODEL_CD", i)
                    .replaceAll("<!HS>", "<b>")
                    .replaceAll("<!HE>", "</b>");
            String modelAmt = search.w3GetField(COLLECTION, "MODEL_AMT", i);
            String carType = search.w3GetField(COLLECTION, "CAR_TYPE", i);
            String odUrl = search.w3GetField(COLLECTION, "OD_URL", i);
            String modelInfo = search.w3GetField(COLLECTION, "MODEL_DISP_FLG", i)
                    .replaceAll("<!HS>", "<b>")
                    .replaceAll("<!HE>", "</b>");
            String engineInfo = search.w3GetField(COLLECTION, "CARINFO_DISP_CAT_NO", i)
                    .replaceAll("<!HS>", "<b>")
                    .replaceAll("<!HE>", "</b>");
            String modelImgAddr = search.w3GetField(COLLECTION, "ELECCAR_YN", i);
            String modelMainImgAddr = search.w3GetField(COLLECTION, "DISP_STAT_CD", i);
            String modelThmnImgAddr = search.w3GetField(COLLECTION, "TAXBEN_YN", i);
            String mktgTxt = search.w3GetField(COLLECTION, "MKTG_TXT", i);
            String benefitsMonth = search.w3GetField(COLLECTION, "BENEFITS_MONTH", i)
                    .replaceAll("<!HS>", "<b>")
                    .replaceAll("<!HE>", "</b>");
            String specialBenefits = search.w3GetField(COLLECTION, "SPECIAL_BENEFITS", i)
                    .replaceAll("<!HS>", "<b>")
                    .replaceAll("<!HE>", "</b>");
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


            PurchaseVo vo = PurchaseVo.builder()
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

            log.debug(vo.toString());
        }

        return SearchVo.builder()
                .collection(COLLECTION)
                .totalCount(totalCount)
                .count(resultCount)
                .result(list)
                .build();
    }

    public SearchVo searchModelTotalList(Map<String, String> params) {

        List<ModelVo> list = new ArrayList<>();

        String query = params.getOrDefault("query", "");

        String COLLECTION = "";
        if (params.containsKey("collection")) {
            COLLECTION = params.get("collection");
            if (COLLECTION.contentEquals("ALL"))
                COLLECTION = "model";
        } else {
            throw new MissingArgumentException("collection은 '필수'값 입니다.");
        }

        int RESULT_COUNT = Integer.parseInt(params.getOrDefault("count", String.valueOf(10))); // 한번에 출력되는 검색 건수
        int PAGE_START = Integer.parseInt(params.getOrDefault("pageStart", String.valueOf(0)));

        List<String> SEARCH_FIELD_LIST = new ArrayList<>();
        SEARCH_FIELD_LIST.add("MODEL_NAME");
        SEARCH_FIELD_LIST.add("MODEL_CD");
        final String SEARCH_FIELD = String.join(",", SEARCH_FIELD_LIST);

        String DOCUMENT_FIELD = "DOCID,DATE,DISP_CAT_NO,LRG_CAT_NO,L_CAT_NM,MID_CAT_NO,M_CAT_NM,UPR_DISP_CAT_NO,SML_CAT_NO,MODEL_NAME,MODEL_CD,MODEL_AMT,CAR_TYPE,MODEL_CAT_IMG_ADDR,MODEL_DISP_FLG,CARINFO_DISP_CAT_NO,ELECCAR_YN,DISP_STAT_CD,TAXBEN_YN,RSV_1_ATTR,RSV_2_ATTR,UPR_DISP_SEQ,MODEL_DISP_SEQ,alias";

        // 정렬필드
        String SORT_FIELD = "";
        if (params.containsKey("sortField") && params.containsKey("sortDirection"))
            SORT_FIELD = params.get("sortField") + "/" + params.get("sortDirection");
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
        for (String field : SEARCH_FIELD_LIST) {
            ret = search.w3AddHighlight(COLLECTION, field);
        }
        ret = search.w3SetSimilarity(COLLECTION, "basic", "BM25", 100);
        ret = search.w3SetQueryAnalyzer(COLLECTION, 1, 1,1, 0);
        ret = search.w3SetRecentQuery(COLLECTION, 1);

        // request
        ret = search.w3ConnectServer(SERVER_IP, SERVER_PORT, SERVER_TIMEOUT);
        ret = search.w3ReceiveSearchQueryResult();

        // check error
        if (!search.w3GetErrorInfo().contentEquals("no error")) {
            log.debug("model 검색 오류 로그 : {}", search.w3GetErrorInfo());
            return null;
        }

        // 전체건수, 결과건수 출력
        int totalCount = search.w3GetResultTotalCount(COLLECTION);
        int resultCount = search.w3GetResultCount(COLLECTION);

        log.debug("model 검색 결과 : {}건 / 전체 건수 : {}건", resultCount, totalCount);

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
                    .replaceAll("<!HS>", "<b>")
                    .replaceAll("<!HE>", "</b>");
            String modelCd = search.w3GetField(COLLECTION, "MODEL_CD", i)
                    .replaceAll("<!HS>", "<b>")
                    .replaceAll("<!HE>", "</b>");
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


            ModelVo vo = ModelVo.builder()
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

            log.debug(vo.toString());
        }


        return SearchVo.builder()
                .collection(COLLECTION)
                .totalCount(totalCount)
                .count(resultCount)
                .result(list)
                .build();
    }

    public SearchVo searchNewsTotalList(Map<String, String> params) {

        List<NewsVo> list = new ArrayList<>();

        String query = params.getOrDefault("query", "");

        String COLLECTION = "";
        if (params.containsKey("collection")) {
            COLLECTION = params.get("collection");
            if (COLLECTION.contentEquals("ALL"))
                COLLECTION = "news";
        } else {
            throw new MissingArgumentException("collection은 '필수'값 입니다.");
        }

        int RESULT_COUNT = Integer.parseInt(params.getOrDefault("count", String.valueOf(10))); // 한번에 출력되는 검색 건수
        int PAGE_START = Integer.parseInt(params.getOrDefault("pageStart", String.valueOf(0)));

        List<String> SEARCH_FIELD_LIST = new ArrayList<>();
        SEARCH_FIELD_LIST.add("TITLE");
        SEARCH_FIELD_LIST.add("CONTENT");
        final String SEARCH_FIELD = String.join(",", SEARCH_FIELD_LIST);

        String DOCUMENT_FIELD = "DOCID,DATE,BB_NO,RN,OPPB_YN,STRT_DT,END_DT,TITLE,CONTENT,UPTN_DISP_YN,REG_DT,SITE_CD,APPX_APPX_FILE_NO,APPX_REG_SEQ,APPX_UPLD_FILE_PATH,APPX_UPLD_FILE_NM,alias";

        // 정렬필드
        String SORT_FIELD = "";
        if (params.containsKey("sortField") && params.containsKey("sortDirection"))
            SORT_FIELD = params.get("sortField") + "/" + params.get("sortDirection");
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
        for (String field : SEARCH_FIELD_LIST) {
            ret = search.w3AddHighlight(COLLECTION, field);
        }
        ret = search.w3SetSimilarity(COLLECTION, "basic", "BM25", 100);
        ret = search.w3SetQueryAnalyzer(COLLECTION, 1, 1,1, 0);
        ret = search.w3SetRecentQuery(COLLECTION, 1);

        // request
        ret = search.w3ConnectServer(SERVER_IP, SERVER_PORT, SERVER_TIMEOUT);
        ret = search.w3ReceiveSearchQueryResult();

        // check error
        if (!search.w3GetErrorInfo().contentEquals("no error")) {
            log.debug("news 검색 오류 로그 : {}", search.w3GetErrorInfo());
            return null;
        }

        // 전체건수, 결과건수 출력
        int totalCount = search.w3GetResultTotalCount(COLLECTION);
        int resultCount = search.w3GetResultCount(COLLECTION);

        log.debug("news 검색 결과 : {}건 / 전체 건수 : {}건", resultCount, totalCount);

        for (int i = 0; i < resultCount; i++) {

            // 기본 검색결과 객체 생성
            String bbNo = search.w3GetField(COLLECTION, "BB_NO", i);
            String rn = search.w3GetField(COLLECTION, "RN", i);
            String oppbYN = search.w3GetField(COLLECTION, "OPPB_YN", i);
            String strtDt = search.w3GetField(COLLECTION, "STRT_DT", i);
            String endDt = search.w3GetField(COLLECTION, "END_DT", i);
            String title = search.w3GetField(COLLECTION, "TITLE", i)
                    .replaceAll("<!HS>", "<b>")
                    .replaceAll("<!HE>", "</b>");
            String content = search.w3GetField(COLLECTION, "CONTENT", i)
                    .replaceAll("<!HS>", "<b>")
                    .replaceAll("<!HE>", "</b>");
            String uptnDispYN = search.w3GetField(COLLECTION, "UPTN_DISP_YN", i);
            String regDt = search.w3GetField(COLLECTION, "REG_DT", i);
            String siteCd = search.w3GetField(COLLECTION, "SITE_CD", i);
            String appxAppxFileNo = search.w3GetField(COLLECTION, "APPX_APPX_FILE_NO", i);
            String appxRegSeq = search.w3GetField(COLLECTION, "APPX_REG_SEQ", i);
            String appxUpldFilePath = search.w3GetField(COLLECTION, "APPX_UPLD_FILE_PATH", i);
            String appxAppxFileNm = search.w3GetField(COLLECTION, "APPX_UPLD_FILE_NM", i);
            float score = search.w3GetRank(COLLECTION, i);


            NewsVo vo = NewsVo.builder()
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
                    .siteCd(siteCd)
                    .appxAppxFileNo(appxAppxFileNo)
                    .appxRegSeq(appxRegSeq)
                    .appxUpldFilePath(appxUpldFilePath)
                    .appxAppxFileNm(appxAppxFileNm)
                    .build();

            list.add(vo);

            log.debug(vo.toString());
        }

        return SearchVo.builder()
                .collection(COLLECTION)
                .totalCount(totalCount)
                .count(resultCount)
                .result(list)
                .build();
    }

    public SearchVo searchEventTotalList(Map<String, String> params) {

        List<EventVo> list = new ArrayList<>();

        String query = params.getOrDefault("query", "");

        String COLLECTION = "";
        if (params.containsKey("collection")) {
            COLLECTION = params.get("collection");
            if (COLLECTION.contentEquals("ALL"))
                COLLECTION = "event";
        } else {
            throw new MissingArgumentException("collection은 '필수'값 입니다.");
        }

        int RESULT_COUNT = Integer.parseInt(params.getOrDefault("count", String.valueOf(10))); // 한번에 출력되는 검색 건수
        int PAGE_START = Integer.parseInt(params.getOrDefault("pageStart", String.valueOf(0)));

        List<String> SEARCH_FIELD_LIST = new ArrayList<>();
        SEARCH_FIELD_LIST.add("TITLE");
        SEARCH_FIELD_LIST.add("CONTENT");
        final String SEARCH_FIELD = String.join(",", SEARCH_FIELD_LIST);

        String DOCUMENT_FIELD = "DOCID,DATE,BB_NO,OPPB_YN,TITLE,CONTENT,BNNR_IMG_URL,STRT_DT,END_DT,REG_DT,APPX_APPX_FILE_NO,APPX_UPLD_FILE_PATH,APPX_UPLD_FILE_NM,STATUS_CD,alias";

        // 정렬필드
        String SORT_FIELD = "";
        if (params.containsKey("sortField") && params.containsKey("sortDirection"))
            SORT_FIELD = params.get("sortField") + "/" + params.get("sortDirection");
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
        for (String field : SEARCH_FIELD_LIST) {
            ret = search.w3AddHighlight(COLLECTION, field);
        }
        ret = search.w3SetSimilarity(COLLECTION, "basic", "BM25", 100);
        ret = search.w3SetQueryAnalyzer(COLLECTION, 1, 1,1, 0);
        ret = search.w3SetRecentQuery(COLLECTION, 1);

        // request
        ret = search.w3ConnectServer(SERVER_IP, SERVER_PORT, SERVER_TIMEOUT);
        ret = search.w3ReceiveSearchQueryResult();

        // check error
        if (!search.w3GetErrorInfo().contentEquals("no error")) {
            log.debug("event 검색 오류 로그 : {}", search.w3GetErrorInfo());
            return null;
        }

        // 전체건수, 결과건수 출력
        int totalCount = search.w3GetResultTotalCount(COLLECTION);
        int resultCount = search.w3GetResultCount(COLLECTION);

        log.debug("event 검색 결과 : {}건 / 전체 건수 : {}건", resultCount, totalCount);

        for (int i = 0; i < resultCount; i++) {

            // 기본 검색결과 객체 생성
            String bbNo = search.w3GetField(COLLECTION, "BB_NO", i);
            String oppbYN = search.w3GetField(COLLECTION, "OPPB_YN", i);
            String title = search.w3GetField(COLLECTION, "TITLE", i)
                    .replaceAll("<!HS>", "<b>")
                    .replaceAll("<!HE>", "</b>");
            String content = search.w3GetField(COLLECTION, "CONTENT", i)
                    .replaceAll("<!HS>", "<b>")
                    .replaceAll("<!HE>", "</b>");
            String bnnrImgUrl = search.w3GetField(COLLECTION, "BNNR_IMG_URL", i);
            String strtDt = search.w3GetField(COLLECTION, "STRT_DT", i);
            String endDt = search.w3GetField(COLLECTION, "END_DT", i);
            String regDt = search.w3GetField(COLLECTION, "REG_DT", i);
            String appxAppxFileNo = search.w3GetField(COLLECTION, "APPX_APPX_FILE_NO", i);
            String appxUpldFilePath = search.w3GetField(COLLECTION, "APPX_UPLD_FILE_PATH", i);
            String appxAppxFileNm = search.w3GetField(COLLECTION, "APPX_UPLD_FILE_NM", i);
            String statusCd = search.w3GetField(COLLECTION, "SATUS_CD", i);
            float score = search.w3GetRank(COLLECTION, i);

            EventVo vo = EventVo.builder()
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

            log.debug(vo.toString());
        }

        return SearchVo.builder()
                .collection(COLLECTION)
                .totalCount(totalCount)
                .count(resultCount)
                .result(list)
                .build();
    }

    public SearchVo searchNoticeTotalList(Map<String, String> params) {

        List<NoticeVo> list = new ArrayList<>();

        String query = params.getOrDefault("query", "");

        String COLLECTION = "";
        if (params.containsKey("collection")) {
            COLLECTION = params.get("collection");
            if (COLLECTION.contentEquals("ALL"))
                COLLECTION = "notice";
        } else {
            throw new MissingArgumentException("collection은 '필수'값 입니다.");
        }

        int RESULT_COUNT = Integer.parseInt(params.getOrDefault("count", String.valueOf(10))); // 한번에 출력되는 검색 건수
        int PAGE_START = Integer.parseInt(params.getOrDefault("pageStart", String.valueOf(0)));

        List<String> SEARCH_FIELD_LIST = new ArrayList<>();
        SEARCH_FIELD_LIST.add("TITLE");
        SEARCH_FIELD_LIST.add("CONTENT");
        final String SEARCH_FIELD = String.join(",", SEARCH_FIELD_LIST);

        String DOCUMENT_FIELD = "DOCID,DATE,BB_NO,RN,OPPB_YN,STRT_DT,END_DT,TITLE,CONTENT,UPTN_DISP_YN,REG_DT,APPX_APPX_FILE_NO,APPX_REG_SEQ,APPX_APPX_FILE_KND_CD,APPX_UPLD_FILE_PATH,APPX_UPLD_FILE_NM,alias";

        // 정렬필드
        String SORT_FIELD = "";
        if (params.containsKey("sortField") && params.containsKey("sortDirection"))
            SORT_FIELD = params.get("sortField") + "/" + params.get("sortDirection");
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
        for (String field : SEARCH_FIELD_LIST) {
            ret = search.w3AddHighlight(COLLECTION, field);
        }
        ret = search.w3SetSimilarity(COLLECTION, "basic", "BM25", 100);
        ret = search.w3SetQueryAnalyzer(COLLECTION, 1, 1,1, 0);
        ret = search.w3SetRecentQuery(COLLECTION, 1);

        log.debug("notice 최근 검색어 : {}", search.w3SetRecentQuery(COLLECTION, 1));

        // 오타교정을 수행할 최소 검색 결과 건수 설정
        ret = search.w3SetSpellCorrectionQuery(COLLECTION, 0);

        // 오타가 교정된 단어를 반환
        String suggestQuery = search.w3GetSuggestedQuery(COLLECTION);
        if(!suggestQuery.contentEquals("")) {
            log.debug("notice 오타 교정 단어 : {}", suggestQuery);
        }

        // request
        ret = search.w3ConnectServer(SERVER_IP, SERVER_PORT, SERVER_TIMEOUT);
        ret = search.w3ReceiveSearchQueryResult();

        // check error
        if (!search.w3GetErrorInfo().contentEquals("no error")) {
            log.debug("notice 검색 오류 로그 : {}", search.w3GetErrorInfo());
            return null;
        }

        // 전체건수, 결과건수 출력
        int totalCount = search.w3GetResultTotalCount(COLLECTION);
        int resultCount = search.w3GetResultCount(COLLECTION);

        log.debug("notice 검색 결과 : {}건 / 전체 건수 : {}건", resultCount, totalCount);

        for (int i = 0; i < resultCount; i++) {

            // 기본 검색결과 객체 생성
            String bbNo = search.w3GetField(COLLECTION, "BB_NO", i);
            String rn = search.w3GetField(COLLECTION, "RN", i);
            String oppbYN = search.w3GetField(COLLECTION, "OPPB_YN", i);
            String strtDt = search.w3GetField(COLLECTION, "STRT_DT", i);
            String endDt = search.w3GetField(COLLECTION, "END_DT", i);
            String title = search.w3GetField(COLLECTION, "TITLE", i)
                    .replaceAll("<!HS>", "<b>")
                    .replaceAll("<!HE>", "</b>");
            String content = search.w3GetField(COLLECTION, "CONTENT", i)
                    .replaceAll("<!HS>", "<b>")
                    .replaceAll("<!HE>", "</b>");
            String uptnDispYN = search.w3GetField(COLLECTION, "UPTN_DISP_YN", i);
            String regDt = search.w3GetField(COLLECTION, "REG_DT", i);
            String appxAppxFileNo = search.w3GetField(COLLECTION, "APPX_APPX_FILE_NO", i);
            String appxRegSeq = search.w3GetField(COLLECTION, "APPX_REG_SEQ", i);
            String appxAppxFileKndCd = search.w3GetField(COLLECTION, "APPX_APPX_FILE_KND_CD", i);
            String appxUpldFilePath = search.w3GetField(COLLECTION, "APPX_UPLD_FILE_PATH", i);
            String appxAppxFileNm = search.w3GetField(COLLECTION, "APPX_UPLD_FILE_NM", i);
            float score = search.w3GetRank(COLLECTION, i);

            NoticeVo vo = NoticeVo.builder()
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

            log.debug(vo.toString());
        }
        return SearchVo.builder()
                .collection(COLLECTION)
                .totalCount(totalCount)
                .count(resultCount)
                .result(list)
                .build();
    }

}

