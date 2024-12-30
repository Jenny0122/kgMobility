package com.wisenut.spring.controller;

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
public class BrokerController {

    final String protocol = "http";
    final int port = 8888;

    // 입력받은 검색어와 정렬정보로 자동완성
    @RequestMapping(value = "/recommend", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Object> retriveRecommendword(@RequestParam(name = "target") String target,
                                                       @RequestParam(name = "query") String query,
                                                       @RequestParam(name = "convert") String convert,
                                                       @RequestParam(name = "lower") String lower) {

        RestTemplate restTemplate = new RestTemplate();

//        final String host = "10.80.1.194"; // 운영1
        final String host = "10.80.1.195"; // 운영2
//        final String host = "10.50.50.41"; // 개발

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

    // 입력받은 label과 조회 범위로 인기검색어 조회
    @RequestMapping(value = "/popular", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Object> retrivePopularword(@RequestParam(name = "label") String label,
                                                     @RequestParam(name = "dateType") String dateType) {

        RestTemplate restTemplate = new RestTemplate();

//        final String host = "10.80.1.194"; // 운영1
        final String host = "10.80.1.195"; // 운영2
//        final String host = "10.50.50.41"; // 개발

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
