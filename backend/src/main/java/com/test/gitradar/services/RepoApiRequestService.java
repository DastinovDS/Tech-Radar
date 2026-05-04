package com.test.gitradar.services;
import com.test.gitradar.exceptions.*;
import com.test.gitradar.models.RepositoryModel;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;


@Service
public class RepoApiRequestService {

    private final WebClient webClient = WebClient.create();

    private <T> T performRequest(String url, ParameterizedTypeReference<T> typeReference, String accessToken){

        WebClient.RequestHeadersSpec<?> request = webClient.get().uri(url);

        if (accessToken != null && !accessToken.isEmpty()) {
            request.header("Authorization", "Bearer " + accessToken);
        }
        // else throw new NullAccessTokenException(); need this only if personal access token will be used for not auth users

        return request
                .retrieve()
                .onStatus(status -> status.value() == 404, response -> Mono.error(new RepositoryNotFoundException()))
                .onStatus(status -> status.value() == 403, response -> Mono.error(new RepositoryForbiddenException()))
                .onStatus(status -> status.value() == 503, response -> Mono.error(new GithubInternalException()))
                .bodyToMono(typeReference)
                .block();
    }

    @Cacheable(value = "github-api-request-cache", key = "#apiString")
    public RepositoryModel fetchRepoData(String apiString, String accessToken) {
        return performRequest(apiString, new ParameterizedTypeReference<>() {}, accessToken);
    }

    @Cacheable(value = "github-api-request-cache", key = "#apiString")
    public List<RepositoryModel> fetchReposData(String apiString, String accessToken) {
        return performRequest(apiString,  new ParameterizedTypeReference<>() {}, accessToken);
    }
}
