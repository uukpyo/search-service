package com.search.multi.service;

import com.search.multi.api.WebClientApi;
import com.search.multi.data.BlogRequestDto;
import com.search.multi.data.dto.api.KakaoBlogApiRequestDto;
import com.search.multi.data.dto.api.KakaoBlogApiResponseDto;
import com.search.multi.data.dto.api.NaverBlogApiReponseDto;
import com.search.multi.data.dto.api.NaverBlogApiRequestDto;
import com.search.multi.data.keyfile.ApiValue;
import com.search.multi.exception.ApiResponseException;
import com.search.multi.service.Interface.ApiService;
import com.search.multi.service.Interface.KakaoApiService;
import com.search.multi.service.Interface.NaverApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApiServiceImpl<T> implements ApiService {

    private final KakaoApiService kakaoApiService;
    private final NaverApiService naverApiService;

    @Override
    public Mono<T> blogSearchApi(BlogRequestDto dto) {
        Mono<T> getApi = kakaoApiService.getKakaoApi(dto)
                .onErrorResume (s-> naverApiService.getNaverApi(dto));
        return getApi;
    }
}
