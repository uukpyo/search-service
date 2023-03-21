package com.search.multi.service.Interface;


import com.search.multi.data.BlogRequestDto;
import com.search.multi.data.dto.api.KakaoBlogApiResponseDto;
import com.search.multi.data.dto.basic.ResponseDto;
import reactor.core.publisher.Mono;

public interface KakaoApiService<T> {
    Mono<T> getKakaoApi(BlogRequestDto blogRequestDto);
}
