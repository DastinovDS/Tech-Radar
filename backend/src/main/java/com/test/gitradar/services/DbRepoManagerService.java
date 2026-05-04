package com.test.gitradar.services;

import com.test.gitradar.models.RepositoryModel;
import com.test.gitradar.models.UserModel;
import com.test.gitradar.repositories.RepoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class DbRepoManagerService {

    @Autowired
    RepoRepository repoRepository;

    @Autowired
    RepoApiRequestService repoApiRequestService;

    @Autowired
    UrlApiBuilderService urlApiBuilderService;

    @Transactional
    public void addNewReposRecord(List<RepositoryModel> repositories, UserModel owner){

        if (repositories == null || repositories.isEmpty()) return;

        Set<Long> repoIds = repoRepository.getRepoIdsByOwner(owner);

        List<RepositoryModel> newRepositories = new ArrayList<>();

        for (RepositoryModel repository : repositories) {
            if (!repoIds.contains(repository.getId())) {
                RepositoryModel newRepo = new RepositoryModel();
                newRepo.setId(repository.getId());
                newRepo.setName(repository.getName());
                newRepo.setIsTracked(false);
                owner.addRepository(newRepo);
                newRepositories.add(newRepo);
            }
        }
        repoRepository.saveAll(newRepositories);
    }

    @Transactional
    public void updateReposRecords(UserModel owner) {

        Set<Long> repoIds = repoRepository.getRepoIdsByIsTracked(owner);

        if (!repoIds.isEmpty()) {
            List<RepositoryModel> apiResponse = repoApiRequestService.fetchReposData(urlApiBuilderService.buildAuthRepoUrl(owner), owner.getAccessToken());
            List<RepositoryModel> reposToUpdate = apiResponse.stream()
                    .filter(repo -> repoIds.contains(repo.getId()))
                    .peek(repo -> {repo.setIsTracked(true);})
                    .toList();
            repoRepository.saveAll(reposToUpdate);
        }
    }

    @Transactional
    public void clearReposRecords(List<Long> ReposToDeactivate, UserModel owner) {

        if (ReposToDeactivate.isEmpty()) return;

        List<RepositoryModel> reposToClear = repoRepository.findAllById(ReposToDeactivate);

        List<RepositoryModel> clearedRepos = reposToClear.stream()
                .filter(repo -> repo.getOwner().equals(owner))
                .map(repo -> {
                    RepositoryModel clearedRepo = new RepositoryModel();
                    clearedRepo.setId(repo.getId());
                    clearedRepo.setName(repo.getName());
                    clearedRepo.setIsTracked(false);
                    return clearedRepo;
                })
                .toList();

        repoRepository.saveAll(clearedRepos);
    }

    @Transactional
    public void activateRepos(List<Long> ReposToActivate, UserModel owner) {
        if (ReposToActivate.isEmpty()) return;

        List<RepositoryModel> reposToActivate = repoRepository.findAllById(ReposToActivate);

        for  (RepositoryModel repository : reposToActivate) {repository.setIsTracked(true);}
        repoRepository.saveAll(reposToActivate);
        updateReposRecords(owner);
    }

    @Transactional
    public void deactivateRepos(List<Long> ReposToDeactivate, UserModel owner) {
        if (ReposToDeactivate.isEmpty()) return;

        clearReposRecords(ReposToDeactivate, owner);
    }
}
