package com.example.searchservice.controller.Rest;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/search/v1")
@Api(tags = {"블로그검색 API"})
@RequiredArgsConstructor
public class RestBlogController {

//    @PostMapping(value = "/join")
//    @ApiOperation(value = "회원가입", response = Join.class)
//    public ResponseEntity<T> signUp() {
//
//    }
}
