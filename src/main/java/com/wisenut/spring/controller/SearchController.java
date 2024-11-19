package com.wisenut.spring.controller;

import com.wisenut.spring.MissingArgumentException;
import com.wisenut.spring.dto.TotalSearchDTO;
import com.wisenut.spring.service.SearchService;
import com.wisenut.spring.vo.SearchVo;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RequiredArgsConstructor
@Tag( name = "KGM 통합검색", description = "KGM 통합검색" )
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
}