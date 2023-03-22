package com.search.multi.data.repository;

import com.search.multi.data.dto.search.BlogResponseDto;
import com.search.multi.data.entity.BlogSearchEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface BlogSearchRepositoryCustom {
    List<BlogResponseDto> findBlogSearchEntityForRank(int cnt);
    List<BlogSearchEntity> findBlogSearchEntityByQuery(String query);
}
