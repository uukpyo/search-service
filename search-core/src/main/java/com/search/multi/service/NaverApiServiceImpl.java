package com.search.multi.service;

import com.search.multi.api.WebClientApi;
import com.search.multi.data.dto.search.BlogRequestDto;
import com.search.multi.data.dto.api.naver.NaverBlogApiReponseDto;
import com.search.multi.data.dto.api.naver.NaverBlogApiRequestDto;
import com.search.multi.data.keyfile.ApiValue;
import com.search.multi.service.Interface.NaverApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Log4j2
public class NaverApiServiceImpl<T> implements NaverApiService {

    private final ApiValue api;
    private final WebClientApi webClientApi;

    @Override
    public Mono<T> getNaverApi(BlogRequestDto blogRequestDto) {
        log.info("NAVER API START");
        String clientId = api.getNaverClientId();
        String secretKey = api.getNaverSecretKey();
        String apiUrl = api.getNaverApiUrl();

        String sort = "";
        if(blogRequestDto.getSort().equals("accuracy")){
            sort = "sim";
        }else if(blogRequestDto.getSort().equals("recency")){
            sort = "date";
        }

        NaverBlogApiRequestDto naverReqDto = NaverBlogApiRequestDto.builder()
                .start(blogRequestDto.getPage())
                .query(blogRequestDto.getQuery())
                .display(10)
                .sort(sort).build();

        MultiValueMap<String, String> naverParamsMap = new LinkedMultiValueMap<>();
        naverParamsMap.add("query", naverReqDto.getQuery());
        naverParamsMap.add("display", String.valueOf(naverReqDto.getDisplay()));
        naverParamsMap.add("start", String.valueOf(naverReqDto.getStart()));
        naverParamsMap.add("sort", naverReqDto.getSort());

        MultiValueMap<String, String> header = new LinkedMultiValueMap<>();
        header.add("X-Naver-Client-Id", clientId);
        header.add("X-Naver-Client-Secret", secretKey);

        NaverBlogApiReponseDto result = new NaverBlogApiReponseDto();
        Mono<T> data =  webClientApi.getApiForMono(apiUrl, naverParamsMap, header, result);
        log.info("NAVER API END");
        return data;
    }
}
