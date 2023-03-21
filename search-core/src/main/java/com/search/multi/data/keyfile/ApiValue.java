package com.search.multi.data.keyfile;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
@ToString
public class ApiValue {

//  kakao
    @Value("${external.api.kakao.key}")
    private String kakaoApiKey;

    @Value("${external.api.kakao.url}")
    private String kakaoApiUrl;


//  naver
    @Value("${external.api.naver.client.id}")
    private String naverClientId;

    @Value("${external.api.naver.client.secret}")
    private String naverSecretKey;

    @Value("${external.api.naver.client.url}")
    private String naverApiUrl;
}
