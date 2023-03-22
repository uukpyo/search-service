package com.search.multi.data.dto.search;

import lombok.Data;

@Data
public class BlogResponseDto {
    private String query;
    private Long cnt;
}
