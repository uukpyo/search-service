package com.search.multi.data.dto.api;

import lombok.Builder;
import lombok.Getter;

@Getter
public class NaverBlogApiRequestDto {
    private String query;
    private String sort;
    private Integer start;
    private Integer display;

    @Builder
    public NaverBlogApiRequestDto(String query, String sort, Integer start, Integer display) {
        this.query = query;
        this.sort = sort;
        this.start = start;
        this.display = display;
    }
}
