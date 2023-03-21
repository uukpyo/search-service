package com.search.multi.data.dto.basic;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum RtnCode {
    SUCCESS(HttpStatus.OK),
    FAIL(HttpStatus.BAD_REQUEST);

    private final HttpStatus httpStatus;

    RtnCode(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}
