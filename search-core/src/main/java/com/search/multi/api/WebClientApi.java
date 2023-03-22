package com.search.multi.api;

import com.search.multi.exception.ApiResponseException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;

@Component
public class WebClientApi<T> {

    public Mono<T> getApiForMono(String url, MultiValueMap<String, String> params, MultiValueMap<String, String> header, T result) throws WebClientResponseException {
        LinkedMultiValueMap mvmap = new LinkedMultiValueMap<>(header);
        Consumer<HttpHeaders> consumer = it -> it.addAll(mvmap);

        return (Mono<T>) WebClient.create(url)
                .get()
                .uri(uriBuilder -> uriBuilder
                        .queryParams(params)
                        .build()
                )
                .headers(consumer)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(httpStatus -> httpStatus != HttpStatus.OK, clientResponse -> {
                    return clientResponse.createException()
                            .flatMap(it -> Mono.error(new ApiResponseException("ERROR : "+clientResponse.statusCode())));
                })
                .bodyToMono(result.getClass());
    }
}
