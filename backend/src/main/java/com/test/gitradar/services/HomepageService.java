package com.test.gitradar.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.test.gitradar.models.RepositoryModel;

import com.test.gitradar.models.UrlModel;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Service
public class HomepageService {

    private final WebClient webClient = WebClient.create();

    private RepositoryModel fetchRepoData(String apiString) {
        JsonNode githubResponse = webClient.get()
                .uri(apiString)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .onErrorResume(e -> Mono.empty())
                .block();

        if (githubResponse != null) {
            RepositoryModel repositoryModel = new RepositoryModel();
            repositoryModel.setId(0L);
            repositoryModel.setStargazersCount(githubResponse.path("stargazers_count").asInt(0));
            repositoryModel.setWatchers(githubResponse.path("watchers_count").asInt(0));
            return  repositoryModel;
        }
        return null;
    }

    public RepositoryModel getRepo(UrlModel url) {
        if (url.getUrl() != null){
            String[] urlParts = url.getSplittedUrl();
            if (urlParts.length >= 5){

                if(("https:".equals(urlParts[0])) && ("".equals(urlParts[1])) && ("github.com".equals(urlParts[2])) && (!"".equals(urlParts[4]))){
                    String owner_name = urlParts[3];
                    String repo_name = urlParts[4];
                    String api_string = "https://api.github.com/repos/" + owner_name + "/" + repo_name;
                    return fetchRepoData(api_string);
                }
            }
        }
        return null;
    }
}
