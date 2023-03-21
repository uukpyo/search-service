package com.search.multi.service.Interface;

import com.search.multi.data.BlogRequestDto;
import reactor.core.publisher.Mono;

public interface ApiService<T> {

    Mono<T> blogSearchApi(BlogRequestDto blogRequestDto);
}
