package com.search.multi.data.dto.api.naver;

import lombok.Data;

import java.util.Date;

@Data
class ItemsDto {
    private String title;
    private String link;
    private String description;
    private String bloggername;
    private String bloggerlink;
    private Date postdate;
}
