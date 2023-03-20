package com.search.searchcore.data.keyfile;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
@ToString
public class ApiKey {
    @Value("${external.api.kakao.key}")
    private String kakaoApiKey;

}
