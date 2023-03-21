package com.search.multi.exception;


import com.search.multi.data.dto.basic.ResponseDto;
import com.search.multi.data.dto.basic.RtnCode;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler
    public ResponseDto errorHandler(RuntimeException e) {
        ResponseDto rtn = new ResponseDto<>();
        rtn.setStatus(RtnCode.FAIL);
        rtn.setData(e.getMessage());
        return rtn;
    }

}
