package com.search.multi.service.Interface;


import com.search.multi.data.dto.search.BlogRequestDto;
import reactor.core.publisher.Mono;

public interface KakaoApiService<T> {
    Mono<T> getKakaoApi(BlogRequestDto blogRequestDto);
}
