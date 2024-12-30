package com.wisenut.spring.controller;

import com.wisenut.spring.dto.TotalSearchRequestDTO;
import com.wisenut.spring.dto.TotalSearchResponseDTO;
import com.wisenut.spring.service.SearchServiceV2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Slf4j
@RestController
public class SearchControllerV2 {

    private final SearchServiceV2 service;

    // "입력받은 검색어와 컬렉션 정보로 국문/영문 통합검색
    @PostMapping(path = {"/search","/search/{language}"}, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> retriveSearchList(@RequestBody @Valid TotalSearchRequestDTO requestDTO, @PathVariable(required = false) String language) {

        language = language == null ? "" : "_" + language;

        TotalSearchResponseDTO dto = service.run(language, requestDTO);

        return ResponseEntity.ok(dto);
    }

}