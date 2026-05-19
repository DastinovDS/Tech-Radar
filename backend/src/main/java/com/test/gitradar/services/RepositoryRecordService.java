package com.test.gitradar.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.test.gitradar.models.RepositoryModel;
import com.test.gitradar.models.RepositoryRecordModel;
import com.test.gitradar.repositories.RepositoryRecordRepository;
import com.test.gitradar.utils.GithubTimeParserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class RepositoryRecordService {

    @Autowired
    RepositoryRecordRepository repositoryRecordRepository;

    @Autowired
    GithubTimeParserUtil githubTimeParserUtil;

    @Autowired
    RepositoryLanguageService repositoryLanguageService;

    public void createSnapshot(RepositoryModel repository, JsonNode json) {
        RepositoryRecordModel record = new RepositoryRecordModel();
        repository.addRepositoryRecord(record);
        record.setLastSyncedAt(LocalDateTime.now());

        // GitHub API info:

        record.setSizeKb(json.path("size").asInt());
        record.setForksCount(json.path("forks").asInt());
        record.setOpenIssuesCount(json.path("open_issues").asInt());
        record.setWatchersCount(json.path("watchers_count").asInt());
        record.setStargazersCount(json.path("stargazers_count").asInt());
        record.setSubscribersCount(json.path("subscribers_count").asInt());

        record.setPushedAt(githubTimeParserUtil.getLocalDateTimeFromString(json.get("pushed_at").asText()));
        record.setUpdatedAt(githubTimeParserUtil.getLocalDateTimeFromString(json.get("updated_at").asText()));

        repositoryLanguageService.addLanguagesToRecord(record); // Adding languages to the record

        repositoryRecordRepository.save(record);
    }
}
