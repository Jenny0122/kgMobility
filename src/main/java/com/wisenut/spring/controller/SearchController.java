package com.wisenut.spring.controller;

import com.wisenut.spring.service.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Slf4j
@RestController
public class SearchController {

    private final SearchService service;

//    @RequestMapping(value = "/search", method = RequestMethod.POST, produces = "application/json")
//    public ResponseEntity<?> retriveSearchList(@RequestBody Map<String, String> params) {
//
//        String collection = params.getOrDefault("collection", "");
//        String query = params.getOrDefault("query", "");
//        params.forEach((key, value) -> log.debug("    - {}: {}", key, value));
//
//        SearchVo purchase = null;
//        SearchVo model = null;
//        SearchVo news = null;
//        SearchVo event = null;
//        SearchVo notice = null;
//        //SearchVo pressEvent = null;
//        SearchVo customer = null;
//        SearchVo brand = null;
//        SearchVo about = null;
//        SearchVo apply = null;
//        SearchVo audit = null;
//
//        try {
//            if (collection.contentEquals("ALL") || collection.contentEquals("purchase"))
//                purchase = service.searchPurchaseTotalList(params);
//            if (collection.contentEquals("ALL") || collection.contentEquals("model"))
//                model = service.searchModelTotalList(params);
//            if (collection.contentEquals("ALL") || collection.contentEquals("news") || collection.contentEquals("pressEvent"))
//                news = service.searchNewsTotalList(params);
//            if (collection.contentEquals("ALL") || collection.contentEquals("event") || collection.contentEquals("pressEvent"))
//                event = service.searchEventTotalList(params);
//            if (collection.contentEquals("ALL") || collection.contentEquals("notice") || collection.contentEquals("pressEvent"))
//                notice = service.searchNoticeTotalList(params);
//            if (collection.contentEquals("ALL") || collection.contentEquals("customer"))
//                customer = service.searchCustomerTotalList(params);
//            if (collection.contentEquals("ALL") || collection.contentEquals("brand"))
//                brand = service.searchBrandTotalList(params);
//            if (collection.contentEquals("ALL") || collection.contentEquals("about"))
//                about = service.searchAboutTotalList(params);
//            if (collection.contentEquals("ALL") || collection.contentEquals("apply"))
//                apply = service.searchApplyTotalList(params);
//            if (collection.contentEquals("ALL") || collection.contentEquals("audit"))
//                audit = service.searchAuditTotalList(params);
//            //if (collection.contentEquals("ALL") || collection.contentEquals("pressEvent"))
//            //    pressEvent = service.searchPressEventTotalList(params);
//        } catch (MissingArgumentException mae) {
//            return ResponseEntity.badRequest()
//                                 .body(mae.toString());
//        }
//
//        TotalSearchResponseDTO dto = TotalSearchResponseDTO.builder()
//                                                           .purchase(purchase)
//                                                           .model(model)
//                                                           .news(news)
//                                                           .event(event)
//                                                           .notice(notice)
//                                                           .customer(customer)
//                                                           .brand(brand)
//                                                           .about(about)
//                                                           .apply(apply)
//                                                           .audit(audit)
//                                                           .query(query)
//                                                           .build();
//        return ResponseEntity.ok(dto);
//    }
//
//
//    @RequestMapping(value = "/search/en", method = RequestMethod.POST, produces = "application/json")
//    public ResponseEntity<?> retriveSearchEnglishList(HttpServletRequest request, @RequestBody Map<String, String> params) {
//
//        String collection = params.getOrDefault("collection", "");
//        String query = params.getOrDefault("query", "");
//        params.forEach((key, value) -> log.debug("    - {}: {}", key, value));
//
//        SearchVo purchase = null;
//        SearchVo model = null;
//        SearchVo news = null;
//        SearchVo event = null;
//        SearchVo notice = null;
//        //SearchVo pressEvent = null;
//        SearchVo customer = null;
//        SearchVo brand = null;
//        SearchVo about = null;
//        SearchVo apply = null;
//        SearchVo audit = null;
//
//        try {
//            if (collection.contentEquals("ALL") || collection.contentEquals("purchase_en"))
//                purchase = service.searchPurchaseTotalList(params);
//            if (collection.contentEquals("ALL") || collection.contentEquals("model_en"))
//                model = service.searchModelTotalList(params);
//            if (collection.contentEquals("ALL") || collection.contentEquals("news_en") || collection.contentEquals("pressEvent_en"))
//                news = service.searchNewsTotalList(params);
//            if (collection.contentEquals("ALL") || collection.contentEquals("event_en") || collection.contentEquals("pressEvent_en"))
//                event = service.searchEventTotalList(params);
//            if (collection.contentEquals("ALL") || collection.contentEquals("notice_en") || collection.contentEquals("pressEvent_en"))
//                notice = service.searchNoticeTotalList(params);
//            if (collection.contentEquals("ALL") || collection.contentEquals("customer_en"))
//                customer = service.searchCustomerTotalList(params);
//            if (collection.contentEquals("ALL") || collection.contentEquals("brand_en"))
//                brand = service.searchBrandTotalList(params);
//            if (collection.contentEquals("ALL") || collection.contentEquals("about_en"))
//                about = service.searchAboutTotalList(params);
//            if (collection.contentEquals("ALL") || collection.contentEquals("apply_en"))
//                apply = service.searchApplyTotalList(params);
//            if (collection.contentEquals("ALL") || collection.contentEquals("audit_en"))
//                audit = service.searchAuditTotalList(params);
//            //if (collection.contentEquals("ALL") || collection.contentEquals("pressEvent"))
//            //    pressEvent = service.searchPressEventTotalList(params);
//        } catch (MissingArgumentException mae) {
//            return ResponseEntity.badRequest()
//                                 .body(mae.toString());
//        }
//
//        TotalSearchResponseDTO dto = TotalSearchResponseDTO.builder()
//                                                           .purchase(purchase)
//                                                           .model(model)
//                                                           .news(news)
//                                                           .event(event)
//                                                           .notice(notice)
//                                                           .customer(customer)
//                                                           .brand(brand)
//                                                           .about(about)
//                                                           .apply(apply)
//                                                           .audit(audit)
//                                                           .query(query)
//                                                           .build();
//        return ResponseEntity.ok(dto);
//    }
}