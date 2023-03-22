package com.search.multi.data.repository;

import com.search.multi.data.dto.search.BlogResponseDto;

import java.util.List;

public interface BlogSearchRepositoryCustom {
    List<BlogResponseDto> findBlogSearchEntityForRank(int cnt);
}
