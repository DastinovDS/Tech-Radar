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

import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

import java.time.LocalDateTime;

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
    public void addRecords(Long userId, List<Long> repoIds){
        UserModel user = findUserOrThrow(userId);
        List<RepositoryModel> repositoryList = repoRepository.findAllByOwnerAndRepoIdIn(user, repoIds);

        if(repositoryList.isEmpty()) return;

        List<Mono<Void>> snapshots = repositoryList.stream()
                .map(repo ->{
                    String apiString = UrlApiBuilderService.buildRepositoryUrl(user, repo);
                    return apiRequestService.performApiRequest(apiString, user.getAccessToken())
                            .flatMap(json ->{
                                repositoryRecordDbService.createSnapshot(repo, Mono.just(json));
                                repo.setLastSyncedAt(LocalDateTime.now());
                                return Mono.empty();
                            })
                            .onErrorResume(throwable -> Mono.empty())
                            .then();
                })
                .toList();

        Mono.when(snapshots).block();

        repoRepository.saveAll(repositoryList);
    }

    @Transactional
    public void deleteRecords(Long repoOwnerId, Long repoId){
        UserModel user = findUserOrThrow(repoOwnerId);
        RepositoryModel repository = findRepositoryOrThrow(user, repoId);

        repositoryRecordRepository.deleteByRepository(repository);
    }

    public void addRepositories(Long userId){
        UserModel user = findUserOrThrow(userId);
        String apiString = UrlApiBuilderService.buildAuthRepoUrl(user);

        JsonNode response = apiRequestService.performApiRequest(apiString, user.getAccessToken()).block();

        if(response != null && response.isArray()){
            List<RepositoryModel> repositoryList = new ArrayList<>();
            for(JsonNode jsonRepo : response){
                if(!repoRepository.existsById(jsonRepo.path("id").asLong())){
                    RepositoryModel repository = new RepositoryModel();

                    repository.setOwner(user);
                    user.addRepository(repository);
                    repository.setTracked(false);

                    repository.setName(jsonRepo.path("name").asText());
                    repository.setRepoId(jsonRepo.path("id").asLong());
                    repository.setLastSyncedAt(LocalDateTime.now());


                    repositoryList.add(repository);
                }
            }

            if(!repositoryList.isEmpty()){
                repoRepository.saveAll(repositoryList);
            }
        }
    }

    @Transactional
    public void activateRepositories(Long userId, List<Long> repoIds){
        UserModel user = findUserOrThrow(userId);
        List<RepositoryModel> repositoryList = repoRepository.findAllByOwnerAndRepoIdIn(user, repoIds);

        if (repositoryList.isEmpty()) return;

        repositoryList.forEach(repo -> repo.setTracked(true));
        repoRepository.saveAll(repositoryList);

        addRecords(userId, repoIds);
    }

    @Transactional
    public void deactivateRepositories(Long userId, List<Long> repoIds){
        UserModel user = findUserOrThrow(userId);
        List<RepositoryModel> repositoryList = repoRepository.findAllByOwnerAndRepoIdIn(user, repoIds);

        if(repositoryList.isEmpty()) return;

        repositoryList.forEach(repository -> {
            repository.setTracked(false);
            repository.clearRepositoryRecords();
        });

        repoRepository.saveAll(repositoryList);
    }
}
