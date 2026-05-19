package com.test.gitradar.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.test.gitradar.models.RepositoryCommitModel;
import com.test.gitradar.models.RepositoryModel;
import com.test.gitradar.models.UserModel;
import com.test.gitradar.repositories.RepoRepository;
import com.test.gitradar.utils.GithubTimeParserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneOffset;
import java.util.Comparator;
import java.util.Set;

@Service
public class RepositoryCommitService {

    @Autowired
    ApiRequestService apiRequestService;

    @Autowired
    RepoRepository repoRepository;

    @Autowired
    GithubTimeParserUtil githubTimeParserUtil;

    @Transactional
    public void syncCommitsAndRepo(Long userId) {

        Set<RepositoryModel> trackedRepos = repoRepository.findAllTrackedByUserId(userId);

        for (RepositoryModel repository : trackedRepos) {
            UserModel user = repository.getOwner();
            String baseApiUrl = UrlApiBuilderService.buildRepositoryCommitsUrl(repository);

            String finalUrl = repository.getRepositoryCommits().stream()
                    .max(Comparator.comparing(RepositoryCommitModel::getCommitDate))
                    .map(latest -> baseApiUrl + "?since=" +
                            latest.getCommitDate().plusSeconds(1).toInstant(ZoneOffset.UTC).toString())
                    .orElse(baseApiUrl);

            JsonNode response = apiRequestService.performApiRequest(finalUrl, user.getAccessToken()).block();

            if (response != null && response.isArray()) {
                for (JsonNode commitJson : response) {
                    try {
                        RepositoryCommitModel newCommit = new RepositoryCommitModel();
                        newCommit.setSha(commitJson.path("sha").asText());

                        String dateString = commitJson.path("commit").path("committer").path("date").asText();
                        newCommit.setCommitDate(githubTimeParserUtil.getLocalDateTimeFromString(dateString));

                        repository.addRepositoryCommit(newCommit);
                    } catch (Exception e) {
                        System.err.println("Error processing commit " + commitJson.path("sha").asText() + ": " + e.getMessage());
                    }
                }
                repoRepository.save(repository);
            }
        }
    }
}
