package com.search.multi.data.dto.api;

import com.search.multi.data.dto.BlogResponseDto;
import lombok.*;

import java.util.Date;
import java.util.List;

@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NaverBlogApiReponseDto {
    private Date lastBuildDate;
    private Integer total;
    private Integer start;
    private Integer display;
    private List<ItemsDto> items;
}

@Data
class ItemsDto{
    private String title;
    private String link;
    private String description;
    private String bloggername;
    private String bloggerlink;
    private Date postdate;
}
