package com.test.gitradar.services;
import com.fasterxml.jackson.databind.JsonNode;
import com.test.gitradar.exceptions.*;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Service
public class ApiRequestService {

    private final WebClient webClient = WebClient.create();

    public Mono<JsonNode> performApiRequest (String url, String accessToken){

        WebClient.RequestHeadersSpec<?> request = webClient.get().uri(url).headers(
                header -> {
                    if (accessToken != null && !accessToken.isEmpty()){
                        header.set("Authorization", "Bearer " + accessToken);
                    }
                }
        );

        return request
                .retrieve()
                .onStatus(status -> status.value() == 404, response -> Mono.error(new RepositoryNotFoundException()))
                .onStatus(status -> status.value() == 403, response -> Mono.error(new RepositoryForbiddenException()))
                .onStatus(status -> status.value() == 503, response -> Mono.error(new GithubInternalException()))
                .bodyToMono(JsonNode.class);
    }
}
