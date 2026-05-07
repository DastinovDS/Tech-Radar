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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

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

    public void addRecord(Long repoOwnerId, Long repoId){

        UserModel user = userRepository.findById(repoOwnerId).orElseThrow(UserNotFoundException::new);
        RepositoryModel repository = repoRepository.getRepoByOwner(user, repoId);

        if(repository != null){
            String url = "";
            Mono<JsonNode> response = apiRequestService.performApiRequest(url, user.getAccessToken());

            repositoryRecordDbService.createSnapshot(repository, response);
        }
        else throw new RepositoryNotFoundException();
    }

    @Transactional
    public void deleteRecords(Long repoOwnerId, Long repoId){
        UserModel user = userRepository.findById(repoOwnerId).orElseThrow(UserNotFoundException::new);
        RepositoryModel repository = repoRepository.getRepoByOwner(user, repoId);

        if(repository == null) throw new RepositoryNotFoundException();

        repositoryRecordRepository.deleteByRepository(repository);
    }
}
