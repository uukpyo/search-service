package com.search.multi.service;

import com.search.multi.api.WebClientApi;
import com.search.multi.data.dto.api.kakao.DocumentsDto;
import com.search.multi.data.dto.api.kakao.KakaoBlogApiRequestDto;
import com.search.multi.data.dto.api.kakao.KakaoBlogApiResponseDto;
import com.search.multi.data.dto.api.kakao.MetaDto;
import com.search.multi.data.dto.api.naver.NaverBlogApiReponseDto;
import com.search.multi.data.dto.api.naver.NaverBlogApiRequestDto;
import com.search.multi.data.dto.search.BlogRequestDto;
import com.search.multi.data.keyfile.ApiValue;
import com.search.multi.exception.ApiResponseException;
import jdk.jfr.Description;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@Log4j2
@RequiredArgsConstructor
@SpringBootTest
@ActiveProfiles("core")
class ApiServiceImplTest<T> {

    @Autowired
    private ApiValue api;
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
    @DisplayName("kakao api 연결 실패일경우 naver api 연결 테스트")
    void kakaoApiFailAndNaverSuccess() {
        String apiUrl = api.getKakaoApiUrl();

        MultiValueMap<String, String> header = new LinkedMultiValueMap<>();
        //header.add("Authorization", "KakaoAK "+ apiKey);

        KakaoBlogApiResponseDto result = new KakaoBlogApiResponseDto();
        Mono<NaverBlogApiReponseDto> data =  webClientApi.getApiForMono(apiUrl, kakaoParamsMap, header, result)
                .onErrorResume(s-> naverApiSuccessFunction());

        var resultNaver = data.block();
        assertThat(resultNaver).isNotNull();
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
        Mono<NaverBlogApiReponseDto> data = webClientApi.getApiForMono(apiUrl, kakaoParamsMap, header, result)
                .onErrorResume(s-> naverApiFailFunction());
        Assertions.assertThatThrownBy(() -> data.block()).isInstanceOf(ApiResponseException.class);
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