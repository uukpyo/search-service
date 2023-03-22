package com.search.multi.service;

import com.search.multi.api.WebClientApi;
import com.search.multi.data.dto.api.kakao.KakaoBlogApiRequestDto;
import com.search.multi.data.dto.api.kakao.KakaoBlogApiResponseDto;
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
class KakaoApiServiceImplTest {

    @Autowired
    private ApiValue api;
    @Autowired
    private WebClientApi webClientApi;
    private KakaoBlogApiRequestDto kakaoReqDto;
    private MultiValueMap<String, String> kakaoParamsMap = new LinkedMultiValueMap<>();


    @BeforeEach
    public void setUp() {
        kakaoReqDto = KakaoBlogApiRequestDto.builder()
                .page(1)
                .query("카카오")
                .size(10)
                .sort("accuracy").build();

        kakaoParamsMap.add("query", kakaoReqDto.getQuery());
        kakaoParamsMap.add("page", String.valueOf(kakaoReqDto.getPage()));
        kakaoParamsMap.add("size", String.valueOf(kakaoReqDto.getSize()));
        kakaoParamsMap.add("sort", kakaoReqDto.getSort());
    }

    @Test
    @DisplayName("kakao api 연결 성공 테스트")
    void kakaoApiSuccess() {
        String apiKey = api.getKakaoApiKey();
        String apiUrl = api.getKakaoApiUrl();

        MultiValueMap<String, String> header = new LinkedMultiValueMap<>();
        header.add("Authorization", "KakaoAK "+ apiKey);

        KakaoBlogApiResponseDto result = new KakaoBlogApiResponseDto();
        Mono<KakaoBlogApiResponseDto> data =  webClientApi.getApiForMono(apiUrl, kakaoParamsMap, header, result);
        result = data.block();
        assertThat(result.getMeta()).isNotNull();
        assertThat(result.getDocuments()).isNotNull();
    }

    @Test
    @DisplayName("kakao api 연결 실패 테스트")
    void kakaoApiFail() {
        String apiKey = api.getKakaoApiKey();
        String apiUrl = api.getKakaoApiUrl();

        MultiValueMap<String, String> header = new LinkedMultiValueMap<>();
        //header.add("Authorization", "KakaoAK "+ apiKey);

        KakaoBlogApiResponseDto result = new KakaoBlogApiResponseDto();
        Mono<KakaoBlogApiResponseDto> data =  webClientApi.getApiForMono(apiUrl, kakaoParamsMap, header, result);
        Assertions.assertThatThrownBy(() -> data.block()).isInstanceOf(ApiResponseException.class);
    }


}