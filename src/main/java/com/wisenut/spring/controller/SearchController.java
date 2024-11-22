package com.wisenut.spring.controller;

import com.wisenut.spring.MissingArgumentException;
import com.wisenut.spring.dto.TotalSearchDTO;
import com.wisenut.spring.service.SearchService;
import com.wisenut.spring.vo.SearchVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.Map;

@RequiredArgsConstructor
@Tag(name = "KGM 통합검색", description = "KGM 통합검색")
@Slf4j
@RestController
public class SearchController {

    private final SearchService service;

    @RequestMapping(value = "/search", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> retriveSearchList(HttpServletRequest request, @RequestBody Map<String, String> params) {

        String collection = params.getOrDefault("collection", "");
        String query = params.getOrDefault("query", "");
        params.forEach((key, value) -> log.debug("    - {}: {}", key, value));

        SearchVo purchase = null;
        SearchVo model = null;
        SearchVo news = null;
        SearchVo event = null;
        SearchVo notice = null;

        try {
            if (collection.contentEquals("ALL") || collection.contentEquals("purchase"))
                purchase = service.searchPurchaseTotalList(params);
            if (collection.contentEquals("ALL") || collection.contentEquals("model"))
                model = service.searchModelTotalList(params);
            if (collection.contentEquals("ALL") || collection.contentEquals("news"))
                news = service.searchNewsTotalList(params);
            if (collection.contentEquals("ALL") || collection.contentEquals("event"))
                event = service.searchEventTotalList(params);
            if (collection.contentEquals("ALL") || collection.contentEquals("notice"))
                notice = service.searchNoticeTotalList(params);
        } catch (MissingArgumentException mae) {
            return ResponseEntity.badRequest()
                    .body(mae.toString());
        }

        TotalSearchDTO dto = TotalSearchDTO.builder()
                .purchase(purchase)
                .model(model)
                .news(news)
                .event(event)
                .notice(notice)
                .query(query)
                .build();
        return ResponseEntity.ok(dto);
    }

    @RequestMapping(value = "/recommend", method = RequestMethod.GET, produces = "application/json")
    @Operation(summary = "자동완성", description = "입력받은 검색어와 정렬정보로 자동완성")
    public ResponseEntity<Object> retriveRecommendword(@RequestParam(name = "target") String target,
                                                 @RequestParam(name = "query") String query, @RequestParam(name = "convert") String convert, @RequestParam(name = "lower") String lower) {


        RestTemplate restTemplate = new RestTemplate();

        final String protocol = "http";
        final String host = "10.50.50.41";
        final int port = 8888;

        final String api = "/ark/api";
        URI uri = UriComponentsBuilder.newInstance().scheme(protocol).host(host).port(port).path(api)
                .queryParam("target", target).queryParam("query", query).queryParam("convert", convert).queryParam("lower", lower).encode().build().toUri();

        log.debug(uri.toString());
        ResponseEntity<Map> response = restTemplate.getForEntity(uri, Map.class);
        return new ResponseEntity<Object>(response.getBody(), response.getStatusCode());
    }

    @RequestMapping(value = "/popular", method = RequestMethod.GET, produces = "application/json")
    @Operation(summary = "인기검색어", description = "입력받은 label과 조회 범위로 인기검색어 조회")
    public ResponseEntity<Object> retrivePopularword(@RequestParam(name = "label") String label,
                                                 @RequestParam(name = "dateType") String dateType) {


        RestTemplate restTemplate = new RestTemplate();

        final String protocol = "http";
        final String host = "10.50.50.41";
        final int port = 8888;

        final String api = "/popular/api";
        URI uri = UriComponentsBuilder.newInstance().scheme(protocol).host(host).port(port).path(api)
                .queryParam("label", label).queryParam("dateType", dateType).encode().build().toUri();

        log.debug(uri.toString());
        ResponseEntity<Map> response = restTemplate.getForEntity(uri, Map.class);
        return new ResponseEntity<Object>(response.getBody(), response.getStatusCode());
    }
}