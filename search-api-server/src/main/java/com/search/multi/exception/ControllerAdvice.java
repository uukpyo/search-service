package com.search.multi.exception;


import com.search.multi.data.dto.basic.ResponseDto;
import com.search.multi.data.dto.basic.RtnCode;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler
    public ResponseDto errorHandler(RuntimeException e) {
        ResponseDto rtn = ResponseDto.builder()
                        .status(RtnCode.FAIL)
                        .data(e.getMessage())
                .build();
        return rtn;
    }

}
