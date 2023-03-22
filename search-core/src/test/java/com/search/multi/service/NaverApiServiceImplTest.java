package com.search.multi.service;

import com.search.multi.api.WebClientApi;
import com.search.multi.data.dto.api.kakao.KakaoBlogApiRequestDto;
import com.search.multi.data.dto.api.naver.NaverBlogApiReponseDto;
import com.search.multi.data.dto.api.naver.NaverBlogApiRequestDto;
import com.search.multi.data.keyfile.ApiValue;
import com.search.multi.exception.ApiResponseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Log4j2
@RequiredArgsConstructor
@SpringBootTest
@ActiveProfiles("core")
class NaverApiServiceImplTest {

    @Autowired
    private ApiValue api;
    @Autowired
    private WebClientApi webClientApi;

    private NaverBlogApiRequestDto naverReqDto;
    private MultiValueMap<String, String> naverParamsMap = new LinkedMultiValueMap<>();

    @BeforeEach
    public void setUp() {
        naverReqDto = NaverBlogApiRequestDto.builder()
                .start(1)
                .query("네이버")
                .display(10)
                .sort("sim").build();

        naverParamsMap.add("query", naverReqDto.getQuery());
        naverParamsMap.add("display", String.valueOf(naverReqDto.getDisplay()));
        naverParamsMap.add("start", String.valueOf(naverReqDto.getStart()));
        naverParamsMap.add("sort", naverReqDto.getSort());
    }

    @Test
    @DisplayName("naver api 연결 성공 테스트")
    void naverApiSuccess() {
        String clientId = api.getNaverClientId();
        String secretKey = api.getNaverSecretKey();
        String apiUrl = api.getNaverApiUrl();

        MultiValueMap<String, String> header = new LinkedMultiValueMap<>();
        header.add("X-Naver-Client-Id", clientId);
        header.add("X-Naver-Client-Secret", secretKey);

        NaverBlogApiReponseDto result = new NaverBlogApiReponseDto();
        Mono<NaverBlogApiReponseDto> data =  webClientApi.getApiForMono(apiUrl, naverParamsMap, header, result);
        result = data.block();
        System.out.println(data);
        System.out.println(result);
        assertThat(result.getDisplay()).isNotNull();
        assertThat(result.getStart()).isNotNull();
        assertThat(result.getLastBuildDate()).isNotNull();
        assertThat(result.getTotal()).isNotNull();
    }

    @Test
    @DisplayName("naver api 연결 실패 테스트")
    void naverApiFail() {
        String clientId = api.getNaverClientId();
        String secretKey = api.getNaverSecretKey();
        String apiUrl = api.getNaverApiUrl();

        MultiValueMap<String, String> header = new LinkedMultiValueMap<>();
        //header.add("X-Naver-Client-Id", clientId);
        //header.add("X-Naver-Client-Secret", secretKey);

        NaverBlogApiReponseDto result = new NaverBlogApiReponseDto();
        Assertions.assertThatThrownBy(() -> webClientApi.getApiForMono(apiUrl, naverParamsMap, header, result).block()).isInstanceOf(ApiResponseException.class);
    }
}