package com.wisenut.spring.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wisenut.spring.KGMSearchApplication;
import com.wisenut.spring.dto.TotalSearchRequestDTO;
import com.wisenut.spring.dto.TotalSearchResponseDTO;
import com.wisenut.spring.service.SearchServiceV2;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

//@WebMvcTest(SearchControllerV2.class)
public class SearchControllerV2Test extends TestConfig{

    @Autowired
    MockMvc mvc;

//    @MockBean
//    private SearchServiceV2 searchService;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void test1() throws Exception {

        // given
        TotalSearchRequestDTO body = TotalSearchRequestDTO.builder()
                                                          .query("토레스")
                                                          .collection("ALL")
                                                          .sortField("SCORE")
                                                          .sortDirection("DESC")
                                                          .pageStart(0)
                                                          .count(10)
                                                          .build();

        //when
        TotalSearchResponseDTO responseDto = getTotalSearchResponseDTO(body);


        // then
        String requestQuery = body.getQuery();
        String responseQuery = responseDto.getQuery();


        assertEquals(requestQuery, responseQuery);

    }

    @Test
    void test2() throws Exception {
        // given
        TotalSearchRequestDTO body = TotalSearchRequestDTO.builder()
                                                          .query("코란도")
                                                          .collection("ALL")
                                                          .sortField("SCORE")
                                                          .sortDirection("DESC")
                                                          .pageStart(0)
                                                          .count(10)
                                                          .build();

        //when
        TotalSearchResponseDTO responseDto = this.getTotalSearchResponseDTO(body);
    }

    private TotalSearchResponseDTO getTotalSearchResponseDTO(TotalSearchRequestDTO body) throws Exception {

        String content = objectMapper.writeValueAsString(body);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/search")
                                                             .contentType(MediaType.APPLICATION_JSON)
                                                             .accept(MediaType.APPLICATION_JSON)
                                                             .content(content))
                              .andExpect(MockMvcResultMatchers.status()
                                                              .isOk())
                              .andDo(MockMvcResultHandlers.print())
                              .andReturn();


        return objectMapper.readValue(result.getResponse().getContentAsString(), TotalSearchResponseDTO.class);
    }
}