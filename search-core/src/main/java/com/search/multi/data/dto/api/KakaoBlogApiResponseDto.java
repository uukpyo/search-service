package com.search.multi.data.dto.api;



import lombok.*;

import java.util.Date;
import java.util.List;

@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KakaoBlogApiResponseDto {
    private MetaDto meta;
    private List<DocumentsDto> documents;

}

    @Data
    class MetaDto{
        private Boolean is_end;
        private Integer total_count;
        private Integer pageable_count;
    }

    @Data
    class DocumentsDto{
        private String title;
        private String contents;
        private String url;
        private String blogname;
        private String thumbnail;
        private Date datetime;
    }

