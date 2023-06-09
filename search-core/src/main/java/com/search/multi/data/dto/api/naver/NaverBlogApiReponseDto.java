package com.search.multi.data.dto.api.naver;


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

