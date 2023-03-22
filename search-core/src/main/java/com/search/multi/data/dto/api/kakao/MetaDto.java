package com.search.multi.data.dto.api.kakao;

import lombok.Data;

@Data
public class MetaDto {
    private Boolean is_end;
    private Integer total_count;
    private Integer pageable_count;
}
