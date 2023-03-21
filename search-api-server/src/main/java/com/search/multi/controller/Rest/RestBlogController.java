package com.search.multi.controller.Rest;

import com.search.multi.data.BlogRequestDto;
import com.search.multi.data.dto.basic.ResponseDto;
import com.search.multi.data.dto.basic.RtnCode;
import com.search.multi.data.keyfile.ApiValue;
import com.search.multi.service.Interface.ApiService;
import com.search.multi.service.KakaoApiServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/blog/v1")
@Api(tags = {"블로그검색 API"})
@RequiredArgsConstructor
@Log4j2
public class RestBlogController {

    private final ApiService apiService;

    @GetMapping(value = "/search")
    @ApiOperation(value = "검색", response = ResponseDto.class)
    public Mono<ResponseDto> blogSearch(@RequestBody BlogRequestDto blogRequestDto) {
        Mono<ResponseDto> rtn = Mono.just(
                ResponseDto.builder()
                        .status(RtnCode.FAIL)
                        .data(apiService.blogSearchApi(blogRequestDto).block())
                        .build()
        );
        return rtn;
    }
}
