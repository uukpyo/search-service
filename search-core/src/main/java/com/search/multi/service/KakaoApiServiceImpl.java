package com.search.multi.service;

import com.search.multi.api.WebClientApi;
import com.search.multi.data.dto.search.BlogRequestDto;
import com.search.multi.data.dto.api.kakao.KakaoBlogApiRequestDto;
import com.search.multi.data.dto.api.kakao.KakaoBlogApiResponseDto;
import com.search.multi.data.keyfile.ApiValue;
import com.search.multi.service.Interface.KakaoApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Log4j2
public class KakaoApiServiceImpl implements KakaoApiService {

    private final ApiValue api;
    private final WebClientApi webClientApi;

    @Override
    public Mono<KakaoBlogApiResponseDto> getKakaoApi(BlogRequestDto blogRequestDto) {
        log.info("KAKAO API START");
        String apiKey = api.getKakaoApiKey();
        String apiUrl = api.getKakaoApiUrl();

        KakaoBlogApiRequestDto kakaoReqDto = KakaoBlogApiRequestDto.builder()
                .page(blogRequestDto.getPage())
                .query(blogRequestDto.getQuery())
                .size(10)
                .sort(blogRequestDto.getSort()).build();

        MultiValueMap<String, String> kakaoParamsMap = new LinkedMultiValueMap<>();
        kakaoParamsMap.add("query", kakaoReqDto.getQuery());
        kakaoParamsMap.add("page", String.valueOf(kakaoReqDto.getPage()));
        kakaoParamsMap.add("size", String.valueOf(kakaoReqDto.getSize()));
        kakaoParamsMap.add("sort", kakaoReqDto.getSort());

        MultiValueMap<String, String> header = new LinkedMultiValueMap<>();
        header.add("Authorization", "KakaoAK "+ apiKey);

        KakaoBlogApiResponseDto result = new KakaoBlogApiResponseDto();
        Mono<KakaoBlogApiResponseDto> data =  webClientApi.getApiForMono(apiUrl, kakaoParamsMap, header, result);
        log.info("KAKAO API END");
        return data;
    }
}
