package com.search.multi.service;

import com.search.multi.data.dto.search.BlogRequestDto;
import com.search.multi.data.dto.search.BlogResponseDto;
import com.search.multi.service.Interface.ApiService;
import com.search.multi.service.Interface.BlogSearchService;
import com.search.multi.service.Interface.KakaoApiService;
import com.search.multi.service.Interface.NaverApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BlogSearchServiceImpl implements BlogSearchService {


    @Override
    public void insertOrUpdateByBlogSearchEntity(BlogRequestDto dto) {

    }

    @Override
    public void insertByBlogSearchEntity(BlogRequestDto dto) {

    }

    @Override
    public void updateByBlogSearchEntity(BlogRequestDto dto) {

    }

    @Override
    public List<BlogResponseDto> getBlogSearchEntityCntRank() {
        return null;
    }
}
