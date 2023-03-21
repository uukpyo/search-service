package com.search.multi;

import com.search.multi.api.WebClientApi;
import com.search.multi.data.dto.api.*;

import com.search.multi.data.dto.basic.ResponseDto;
import com.search.multi.data.dto.basic.RtnCode;
import com.search.multi.data.keyfile.ApiValue;
import com.search.multi.exception.ApiResponseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Log4j2
@RequiredArgsConstructor
@SpringBootTest
@ActiveProfiles("core")
class ApiTest {

    @Autowired private ApiValue api;
    @Autowired private WebClientApi webClientApi;
    private KakaoBlogApiRequestDto kakaoReqDto;
    private MultiValueMap<String, String> kakaoParamsMap = new LinkedMultiValueMap<>();
    private NaverBlogApiRequestDto naverReqDto;
    private MultiValueMap<String, String> naverParamsMap = new LinkedMultiValueMap<>();

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
    @DisplayName("kakao api 연결 실패 테스트")
    void kakaoApiFail() {
        String apiKey = api.getKakaoApiKey();
        String apiUrl = api.getKakaoApiUrl();

        MultiValueMap<String, String> header = new LinkedMultiValueMap<>();
        //header.add("Authorization", "KakaoAK "+ apiKey);

        KakaoBlogApiResponseDto result = new KakaoBlogApiResponseDto();
        Mono<KakaoBlogApiResponseDto> data =  webClientApi.getApiForMono(apiUrl, kakaoParamsMap, header, result);
        result = data.block();
        assertThat(result.getMeta()).isNull();
        assertThat(result.getDocuments()).isNull();
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

    @Test
    @DisplayName("kakao api 연결 실패일경우 naver api 연결 테스트")
    void kakaoApiFailAndNaverSuccess() {
        String apiUrl = api.getKakaoApiUrl();

        MultiValueMap<String, String> header = new LinkedMultiValueMap<>();
        //header.add("Authorization", "KakaoAK "+ apiKey);

        KakaoBlogApiResponseDto result = new KakaoBlogApiResponseDto();
        NaverBlogApiReponseDto resultNaver = new NaverBlogApiReponseDto();
        Mono<KakaoBlogApiResponseDto> data =  webClientApi.getApiForMono(apiUrl, kakaoParamsMap, header, result);
        var resultData = data.blockOptional().get().getDocuments();

        if(resultData == null){
            Mono<NaverBlogApiReponseDto> naverData =  naverApiSuccessFunction();
            resultNaver = naverData.block();
        }
        assertThat(resultNaver.getDisplay()).isNotNull();
        assertThat(resultNaver.getStart()).isNotNull();
        assertThat(resultNaver.getLastBuildDate()).isNotNull();
        assertThat(resultNaver.getTotal()).isNotNull();
    }

    @Test
    @DisplayName("kakao api 연결 실패, naver api 연결 실패일경우 오류응답 테스트")
    void kakaoApiFailAndNaverFail() {
        String apiUrl = api.getKakaoApiUrl();

        MultiValueMap<String, String> header = new LinkedMultiValueMap<>();
        //header.add("Authorization", "KakaoAK "+ apiKey);

        KakaoBlogApiResponseDto result = new KakaoBlogApiResponseDto();
        Mono<KakaoBlogApiResponseDto> data =  webClientApi.getApiForMono(apiUrl, kakaoParamsMap, header, result);
        var resultData = data.blockOptional().get().getDocuments();

        if(resultData == null){
            Mono<NaverBlogApiReponseDto> naverData =  naverApiFailFunction();
            Assertions.assertThatThrownBy(() -> naverData.block()).isInstanceOf(ApiResponseException.class);
        }
    }



    private Mono<NaverBlogApiReponseDto> naverApiSuccessFunction() {
        String clientId = api.getNaverClientId();
        String secretKey = api.getNaverSecretKey();
        String apiUrl = api.getNaverApiUrl();

        MultiValueMap<String, String> header = new LinkedMultiValueMap<>();
        header.add("X-Naver-Client-Id", clientId);
        header.add("X-Naver-Client-Secret", secretKey);

        NaverBlogApiReponseDto result = new NaverBlogApiReponseDto();

        Mono<NaverBlogApiReponseDto> data =  webClientApi.getApiForMono(apiUrl, naverParamsMap, header, result);
        return data;
    }

    private Mono<NaverBlogApiReponseDto> naverApiFailFunction() {
        String clientId = api.getNaverClientId();
        String secretKey = api.getNaverSecretKey();
        String apiUrl = api.getNaverApiUrl();

        MultiValueMap<String, String> header = new LinkedMultiValueMap<>();
//        header.add("X-Naver-Client-Id", clientId);
        header.add("X-Naver-Client-Secret", secretKey);

        NaverBlogApiReponseDto result = new NaverBlogApiReponseDto();

        Mono<NaverBlogApiReponseDto> data =  webClientApi.getApiForMono(apiUrl, naverParamsMap, header, result);
        return data;
    }
}