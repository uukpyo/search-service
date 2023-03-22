package com.search.multi.service;

import com.search.multi.data.dto.search.BlogRequestDto;
import com.search.multi.service.Interface.ApiService;
import com.search.multi.service.Interface.BlogSearchService;
import com.search.multi.service.Interface.KakaoApiService;
import com.search.multi.service.Interface.NaverApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BlogSearchServiceImpl<T> implements BlogSearchService {


}
