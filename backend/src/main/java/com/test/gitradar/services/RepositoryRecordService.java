package com.test.gitradar.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.test.gitradar.models.RepositoryModel;
import com.test.gitradar.models.RepositoryRecordModel;
import com.test.gitradar.repositories.RepositoryRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RepositoryRecordService {

    @Autowired
    RepositoryRecordRepository repositoryRecordRepository;

    public void createSnapshot(RepositoryModel repository, JsonNode json) {
        RepositoryRecordModel record = new RepositoryRecordModel();
        record.setOwnerRepo(repository);
        repository.addRepositoryRecord(record);
        record.setLastSyncedAt(LocalDateTime.now());
        record.setTestString(json.path("name").asText());

        repositoryRecordRepository.save(record);
    }
}
