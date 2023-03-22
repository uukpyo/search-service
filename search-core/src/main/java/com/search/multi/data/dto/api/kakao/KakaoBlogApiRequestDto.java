package com.search.multi.data.dto.api.kakao;

import lombok.Builder;
import lombok.Getter;

@Getter
public class KakaoBlogApiRequestDto {
    private String query;
    private String sort;
    private Integer page;
    private Integer size;

    @Builder
    public KakaoBlogApiRequestDto(String query, String sort, Integer page, Integer size) {
        this.query = query;
        this.sort = sort;
        this.page = page;
        this.size = size;
    }
}
