package com.search.multi.service.Interface;

import com.search.multi.data.dto.search.BlogRequestDto;
import com.search.multi.data.dto.search.BlogResponseDto;
import com.search.multi.data.entity.BlogSearchEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

public interface BlogSearchService {
    void insertOrUpdateByBlogSearchEntity(BlogRequestDto dto);
    void insertByBlogSearchEntity(BlogRequestDto dto);
    void updateByBlogSearchEntity(BlogSearchEntity blogSearchEntity);
    List<BlogResponseDto> getBlogSearchEntityCntRank();

}
