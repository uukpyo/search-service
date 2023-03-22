package com.search.multi.data.dto.api.kakao;



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

