package com.search.multi.service.Interface;

import com.search.multi.data.dto.search.BlogRequestDto;
import com.search.multi.data.dto.search.BlogResponseDto;

import java.util.List;

public interface BlogSearchService {
    void insertOrUpdateByBlogSearchEntity(BlogRequestDto dto);
    void insertByBlogSearchEntity(BlogRequestDto dto);
    void updateByBlogSearchEntity(BlogRequestDto dto);
    List<BlogResponseDto> getBlogSearchEntityCntRank();
}
