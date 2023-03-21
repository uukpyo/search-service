package com.search.multi.service.Interface;


import com.search.multi.data.BlogRequestDto;
import com.search.multi.data.dto.api.NaverBlogApiReponseDto;
import reactor.core.publisher.Mono;

public interface NaverApiService<T> {
    Mono<T> getNaverApi(BlogRequestDto blogRequestDto);
}
