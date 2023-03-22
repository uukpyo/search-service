package com.search.multi.data.dto.api.kakao;

import lombok.Data;

import java.util.Date;

@Data
public class DocumentsDto {
    private String title;
    private String contents;
    private String url;
    private String blogname;
    private String thumbnail;
    private Date datetime;
}
