package com.wisenut.spring.controller;

import com.wisenut.spring.MissingArgumentException;
import com.wisenut.spring.dto.TotalSearchRequestDTO;
import com.wisenut.spring.dto.TotalSearchResponseDTO;
import com.wisenut.spring.service.SearchService;
import com.wisenut.spring.vo.SearchVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Tag(name = "KGM 통합검색", description = "KGM 통합검색")
@Slf4j
@RestController
public class SearchController {

    private final SearchService service;

    @RequestMapping(value = "/search", method = RequestMethod.POST, produces = "application/json")
    @Operation(summary = "국문 통합검색", description = "입력받은 검색어와 컬렉션 정보로 국문 통합검색")
    public ResponseEntity<?> retriveSearchList(HttpServletRequest request, @RequestBody Map<String, String> params) {

        String collection = params.getOrDefault("collection", "");
        String query = params.getOrDefault("query", "");
        params.forEach((key, value) -> log.debug("    - {}: {}", key, value));

        SearchVo purchase = null;
        SearchVo model = null;
        SearchVo news = null;
        SearchVo event = null;
        SearchVo notice = null;
        //SearchVo pressEvent = null;
        SearchVo customer = null;
        SearchVo brand = null;
        SearchVo about = null;
        SearchVo apply = null;
        SearchVo audit = null;

        try {
            if (collection.contentEquals("ALL") || collection.contentEquals("purchase"))
                purchase = service.searchPurchaseTotalList(params);
            if (collection.contentEquals("ALL") || collection.contentEquals("model"))
                model = service.searchModelTotalList(params);
            if (collection.contentEquals("ALL") || collection.contentEquals("news") || collection.contentEquals("pressEvent"))
                news = service.searchNewsTotalList(params);
            if (collection.contentEquals("ALL") || collection.contentEquals("event") || collection.contentEquals("pressEvent"))
                event = service.searchEventTotalList(params);
            if (collection.contentEquals("ALL") || collection.contentEquals("notice") || collection.contentEquals("pressEvent"))
                notice = service.searchNoticeTotalList(params);
            if (collection.contentEquals("ALL") || collection.contentEquals("customer"))
                customer = service.searchCustomerTotalList(params);
            if (collection.contentEquals("ALL") || collection.contentEquals("brand"))
                brand = service.searchBrandTotalList(params);
            if (collection.contentEquals("ALL") || collection.contentEquals("about"))
                about = service.searchAboutTotalList(params);
            if (collection.contentEquals("ALL") || collection.contentEquals("apply"))
                apply = service.searchApplyTotalList(params);
            if (collection.contentEquals("ALL") || collection.contentEquals("audit"))
                audit = service.searchAuditTotalList(params);
            //if (collection.contentEquals("ALL") || collection.contentEquals("pressEvent"))
            //    pressEvent = service.searchPressEventTotalList(params);
        } catch (MissingArgumentException mae) {
            return ResponseEntity.badRequest()
                                 .body(mae.toString());
        }

        TotalSearchResponseDTO dto = TotalSearchResponseDTO.builder()
                                                           .purchase(purchase)
                                                           .model(model)
                                                           .news(news)
                                                           .event(event)
                                                           .notice(notice)
                                                           .customer(customer)
                                                           .brand(brand)
                                                           .about(about)
                                                           .apply(apply)
                                                           .audit(audit)
                                                           .query(query)
                                                           .build();
        return ResponseEntity.ok(dto);
    }

    @RequestMapping(value = "/v2/search", method = RequestMethod.POST, produces = "application/json")
    @Operation(summary = "국문 통합검색", description = "입력받은 검색어와 컬렉션 정보로 국문 통합검색")
    public ResponseEntity<?> retriveSearchListNew(HttpServletRequest request, @RequestBody @Valid TotalSearchRequestDTO requestDTO) {

        final String LANGUAGE = "korean";

        String collection = requestDTO.getCollection();
        String query = requestDTO.getQuery();

        SearchVo purchase = null;
        SearchVo model = null;
        SearchVo news = null;
        SearchVo event = null;
        SearchVo notice = null;
        //SearchVo pressEvent = null;
        SearchVo customer = null;
        SearchVo brand = null;
        SearchVo about = null;
        SearchVo apply = null;
        SearchVo audit = null;

        try {
            if (collection.contentEquals("ALL") || collection.contentEquals("purchase"))
                purchase = service.searchPurchaseTotalListNew(LANGUAGE, requestDTO);
            if (collection.contentEquals("ALL") || collection.contentEquals("model"))
                model = service.searchPurchaseTotalListNew(LANGUAGE, requestDTO);
            if (collection.contentEquals("ALL") || collection.contentEquals("news") || collection.contentEquals("pressEvent"))
                news = service.searchPurchaseTotalListNew(LANGUAGE, requestDTO);
            if (collection.contentEquals("ALL") || collection.contentEquals("event") || collection.contentEquals("pressEvent"))
                event = service.searchPurchaseTotalListNew(LANGUAGE, requestDTO);
            if (collection.contentEquals("ALL") || collection.contentEquals("notice") || collection.contentEquals("pressEvent"))
                notice = service.searchPurchaseTotalListNew(LANGUAGE, requestDTO);
            if (collection.contentEquals("ALL") || collection.contentEquals("customer"))
                customer = service.searchPurchaseTotalListNew(LANGUAGE, requestDTO);
            if (collection.contentEquals("ALL") || collection.contentEquals("brand"))
                brand = service.searchPurchaseTotalListNew(LANGUAGE, requestDTO);
            if (collection.contentEquals("ALL") || collection.contentEquals("about"))
                about = service.searchPurchaseTotalListNew(LANGUAGE, requestDTO);
            if (collection.contentEquals("ALL") || collection.contentEquals("apply"))
                apply = service.searchPurchaseTotalListNew(LANGUAGE, requestDTO);
            if (collection.contentEquals("ALL") || collection.contentEquals("audit"))
                audit = service.searchPurchaseTotalListNew(LANGUAGE, requestDTO);
            //if (collection.contentEquals("ALL") || collection.contentEquals("pressEvent"))
            //    pressEvent = service.searchPressEventTotalList(params);
        } catch (MissingArgumentException mae) {
            return ResponseEntity.badRequest()
                                 .body(mae.toString());
        }

        TotalSearchResponseDTO dto = TotalSearchResponseDTO.builder()
                                                           .purchase(purchase)
                                                           .model(model)
                                                           .news(news)
                                                           .event(event)
                                                           .notice(notice)
                                                           .customer(customer)
                                                           .brand(brand)
                                                           .about(about)
                                                           .apply(apply)
                                                           .audit(audit)
                                                           .query(query)
                                                           .build();
        return ResponseEntity.ok(dto);
    }

    @RequestMapping(value = "/search/en", method = RequestMethod.POST, produces = "application/json")
    @Operation(summary = "영문 통합검색", description = "입력받은 검색어와 컬렉션 정보로 영문 통합검색")
    public ResponseEntity<?> retriveSearchEnglishList(HttpServletRequest request, @RequestBody Map<String, String> params) {

        String collection = params.getOrDefault("collection", "");
        String query = params.getOrDefault("query", "");
        params.forEach((key, value) -> log.debug("    - {}: {}", key, value));

        SearchVo purchase = null;
        SearchVo model = null;
        SearchVo news = null;
        SearchVo event = null;
        SearchVo notice = null;
        //SearchVo pressEvent = null;
        SearchVo customer = null;
        SearchVo brand = null;
        SearchVo about = null;
        SearchVo apply = null;
        SearchVo audit = null;

        try {
            if (collection.contentEquals("ALL") || collection.contentEquals("purchase_en"))
                purchase = service.searchPurchaseTotalList(params);
            if (collection.contentEquals("ALL") || collection.contentEquals("model_en"))
                model = service.searchModelTotalList(params);
            if (collection.contentEquals("ALL") || collection.contentEquals("news_en") || collection.contentEquals("pressEvent_en"))
                news = service.searchNewsTotalList(params);
            if (collection.contentEquals("ALL") || collection.contentEquals("event_en") || collection.contentEquals("pressEvent_en"))
                event = service.searchEventTotalList(params);
            if (collection.contentEquals("ALL") || collection.contentEquals("notice_en") || collection.contentEquals("pressEvent_en"))
                notice = service.searchNoticeTotalList(params);
            if (collection.contentEquals("ALL") || collection.contentEquals("customer_en"))
                customer = service.searchCustomerTotalList(params);
            if (collection.contentEquals("ALL") || collection.contentEquals("brand_en"))
                brand = service.searchBrandTotalList(params);
            if (collection.contentEquals("ALL") || collection.contentEquals("about_en"))
                about = service.searchAboutTotalList(params);
            if (collection.contentEquals("ALL") || collection.contentEquals("apply_en"))
                apply = service.searchApplyTotalList(params);
            if (collection.contentEquals("ALL") || collection.contentEquals("audit_en"))
                audit = service.searchAuditTotalList(params);
            //if (collection.contentEquals("ALL") || collection.contentEquals("pressEvent"))
            //    pressEvent = service.searchPressEventTotalList(params);
        } catch (MissingArgumentException mae) {
            return ResponseEntity.badRequest()
                                 .body(mae.toString());
        }

        TotalSearchResponseDTO dto = TotalSearchResponseDTO.builder()
                                                           .purchase(purchase)
                                                           .model(model)
                                                           .news(news)
                                                           .event(event)
                                                           .notice(notice)
                                                           .customer(customer)
                                                           .brand(brand)
                                                           .about(about)
                                                           .apply(apply)
                                                           .audit(audit)
                                                           .query(query)
                                                           .build();
        return ResponseEntity.ok(dto);
    }

    @RequestMapping(value = "/recommend", method = RequestMethod.GET, produces = "application/json")
    @Operation(summary = "자동완성", description = "입력받은 검색어와 정렬정보로 자동완성")
    public ResponseEntity<Object> retriveRecommendword(@RequestParam(name = "target") String target,
                                                       @RequestParam(name = "query") String query,
                                                       @RequestParam(name = "convert") String convert,
                                                       @RequestParam(name = "lower") String lower) {

        RestTemplate restTemplate = new RestTemplate();

        final String protocol = "http";
        final String host = "10.80.1.194"; // 운영1
//        final String host = "10.80.1.195"; // 운영2
//        final String host = "10.50.50.41"; // 개발
        final int port = 8888;

        final String api = "/ark/api";
        URI uri = UriComponentsBuilder.newInstance()
                                      .scheme(protocol)
                                      .host(host)
                                      .port(port)
                                      .path(api)
                                      .queryParam("target", target)
                                      .queryParam("query", query)
                                      .queryParam("convert", convert)
                                      .queryParam("lower", lower)
                                      .encode()
                                      .build()
                                      .toUri();

        try {
            log.debug(uri.toString());
            // External API 호출
            ResponseEntity<Map> response = restTemplate.getForEntity(uri, Map.class);

            // 정상적인 응답이 있으면 그대로 반환
            return new ResponseEntity<>(response.getBody(), response.getStatusCode());

        } catch (Exception e) {
            // 예외 발생 시 500 오류 처리 (응답을 커스터마이즈)
            log.error("External API 호출 중 오류 발생", e);

            // 500 오류 대신 4004 코드와 메시지를 반환
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("code", 4004);
            errorResponse.put("result", "");
            errorResponse.put("message", "자동 완성이 불가능합니다.");

            return new ResponseEntity<>(errorResponse, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/popular", method = RequestMethod.GET, produces = "application/json")
    @Operation(summary = "인기검색어", description = "입력받은 label과 조회 범위로 인기검색어 조회")
    public ResponseEntity<Object> retrivePopularword(@RequestParam(name = "label") String label,
                                                     @RequestParam(name = "dateType") String dateType) {

        RestTemplate restTemplate = new RestTemplate();

        final String protocol = "http";

        final String host = "10.80.1.194"; // 운영1
//        final String host = "10.80.1.195"; // 운영2

//        final String host = "10.50.50.41"; // 개발
        final int port = 8888;

        final String api = "/popular/api";
        URI uri = UriComponentsBuilder.newInstance()
                                      .scheme(protocol)
                                      .host(host)
                                      .port(port)
                                      .path(api)
                                      .queryParam("label", label)
                                      .queryParam("dateType", dateType)
                                      .encode()
                                      .build()
                                      .toUri();

        log.debug(uri.toString());
        ResponseEntity<Map> response = restTemplate.getForEntity(uri, Map.class);
        return new ResponseEntity<Object>(response.getBody(), response.getStatusCode());
    }
}