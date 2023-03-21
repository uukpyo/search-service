package com.search.multi.data;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
public class BlogRequestDto {
    private String query;
    private String sort;
    private int page;
}
