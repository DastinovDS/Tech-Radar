package com.test.gitradar.services;

import com.fasterxml.jackson.databind.JsonNode;

import com.test.gitradar.exceptions.RepositoryNotFoundException;
import com.test.gitradar.exceptions.UserNotFoundException;

import com.test.gitradar.models.RepositoryModel;
import com.test.gitradar.models.UserModel;

import com.test.gitradar.repositories.RepoRepository;
import com.test.gitradar.repositories.RepositoryRecordRepository;
import com.test.gitradar.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.stereotype.Service;

import java.util.List;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RepositoryService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RepoRepository repoRepository;

    @Autowired
    RepositoryRecordDbService  repositoryRecordDbService;

    @Autowired
    RepositoryRecordRepository repositoryRecordRepository;

    @Autowired
    ApiRequestService apiRequestService;

    private UserModel findUserOrThrow(Long userId) {
        return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }

    private RepositoryModel findRepositoryOrThrow(UserModel user, Long repoId) {
        RepositoryModel repository = repoRepository.getRepoByOwner(user, repoId);
        if (repository != null) return repository;
        else throw new RepositoryNotFoundException();
    }

    @Transactional
    public void addRecords(Long userId, List<Long> repoIds) {
        UserModel user = findUserOrThrow(userId);

        Set<RepositoryModel> repositorySet = repoRepository.findAllByOwnerAndRepoIdIn(user, repoIds);

        if (repositorySet.isEmpty()) return;

        for (RepositoryModel repo : repositorySet) {
            try {
                String apiString = UrlApiBuilderService.buildRepositoryUrl(user, repo);
                JsonNode json = apiRequestService.performApiRequest(apiString, user.getAccessToken()).block();

                if (json != null) {
                    repositoryRecordDbService.createSnapshot(repo, json);
                    repo.setLastSyncedAt(LocalDateTime.now());
                }
            } catch (Exception e) {
                System.err.println("Failed to sync repo: " + repo.getRepoId() + " Error: " + e.getMessage());
            }
        }

        repoRepository.saveAll(repositorySet);
    }

    @Transactional
    public void deleteRecords(Long repoOwnerId, Long repoId){
        UserModel user = findUserOrThrow(repoOwnerId);
        RepositoryModel repository = findRepositoryOrThrow(user, repoId);

        repository.clearRepositoryRecords();
    }

    @Transactional
    public void addRepositories(Long userId) {
        UserModel user = findUserOrThrow(userId);
        String apiString = UrlApiBuilderService.buildAuthRepoUrl(user);

        JsonNode response = apiRequestService.performApiRequest(apiString, user.getAccessToken()).block();

        if (response != null && response.isArray()) {
            Set<Long> existingRepoIds = user.getRepositories().stream()
                    .map(RepositoryModel::getRepoId)
                    .collect(Collectors.toSet());

            for (JsonNode jsonRepo : response) {
                Long githubRepoId = jsonRepo.path("id").asLong();

                if (!existingRepoIds.contains(githubRepoId)) {
                    RepositoryModel repository = new RepositoryModel();
                    repository.setRepoId(githubRepoId);
                    repository.setTracked(false);
                    repository.setLastSyncedAt(LocalDateTime.now());
                    repository.setName(jsonRepo.path("name").asText());

                    repository.setOwner(user);
                    user.addRepository(repository);

                }
            }
            userRepository.save(user);
        }
    }

    @Transactional
    public void activateRepositories(Long userId, List<Long> repoIds){
        UserModel user = findUserOrThrow(userId);
        Set<RepositoryModel> repositorySet = repoRepository.findAllByOwnerAndRepoIdIn(user, repoIds);

        if (repositorySet.isEmpty()) return;

        repositorySet.forEach(repo -> repo.setTracked(true));
        repoRepository.saveAll(repositorySet);

        addRecords(userId, repoIds);
    }

    @Transactional
    public void deactivateRepositories(Long userId, List<Long> repoIds){
        UserModel user = findUserOrThrow(userId);
        Set<RepositoryModel> repositorySet = repoRepository.findAllByOwnerAndRepoIdIn(user, repoIds);

        if(repositorySet.isEmpty()) return;

        repositorySet.forEach(repository -> {
            repository.setTracked(false);
            repository.clearRepositoryRecords();
        });

        repoRepository.saveAll(repositorySet);
    }
}
