package com.search.multi.data.dto.basic;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseDto<T>  {
    private T data;
    private RtnCode status;
}
