package com.test.gitradar.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.test.gitradar.models.RepositoryModel;
import com.test.gitradar.models.RepositoryRecordModel;
import com.test.gitradar.repositories.RepositoryRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class RepositoryRecordDbService {

    @Autowired
    RepositoryRecordRepository repositoryRecordRepository;

    public void createSnapshot(RepositoryModel repository, Mono<JsonNode> response) {

        if(repository == null) throw new IllegalArgumentException("RepositoryModel is null");

        response.map(json -> {

            // Creating model instance
            RepositoryRecordModel record = new RepositoryRecordModel();

            // Setting significant attributes
            record.setOwnerRepo(repository);
            record.setLastSyncedAt(LocalDateTime.now());

            // Adding statistic info

            record.setTestString(json.path("name").asText());

            return record;
        }).doOnNext(record -> {
            repositoryRecordRepository.save(record);
        }).subscribe();
    }
}
