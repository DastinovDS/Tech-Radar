package com.test.gitradar.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")

public class UserModel {
    @Id
    private Long githubId;

    private String login;
    private String accessToken;
    private String avatarUrl;

    private LocalDateTime lastSync;
    private boolean hasCompletedOnboarding = false;
    private boolean trackedForLeaderboard = false;

    private Integer githubApiLimit;
    private Integer githubApiRemaining;
    private Long githubApiResetTime;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RepositoryModel> repositories =  new HashSet<>();

    public void addRepository(RepositoryModel repository){
        repositories.add(repository);
        repository.setOwner(this);
    }

    public Long getGithubId() {
        return githubId;
    }

    public void setGithubId(Long githubId) {
        this.githubId = githubId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public LocalDateTime getLastSync() {
        return lastSync;
    }

    public void setLastSync(LocalDateTime lastSync) {
        this.lastSync = lastSync;
    }

    public boolean isHasCompletedOnboarding() {
        return hasCompletedOnboarding;
    }

    public void setHasCompletedOnboarding(boolean hasCompletedOnboarding) {
        this.hasCompletedOnboarding = hasCompletedOnboarding;
    }

    public boolean isTrackedForLeaderboard() {
        return trackedForLeaderboard;
    }

    public void setTrackedForLeaderboard(boolean trackedForLeaderboard) {
        this.trackedForLeaderboard = trackedForLeaderboard;
    }

    public Integer getGithubApiLimit() {
        return githubApiLimit;
    }

    public void setGithubApiLimit(Integer githubApiLimit) {
        this.githubApiLimit = githubApiLimit;
    }

    public Integer getGithubApiRemaining() {
        return githubApiRemaining;
    }

    public void setGithubApiRemaining(Integer githubApiRemaining) {
        this.githubApiRemaining = githubApiRemaining;
    }

    public Long getGithubApiResetTime() {
        return githubApiResetTime;
    }

    public void setGithubApiResetTime(Long githubApiResetTime) {
        this.githubApiResetTime = githubApiResetTime;
    }

    public Set<RepositoryModel> getRepositories() {
        return repositories;
    }

    public void setRepositories(Set<RepositoryModel> repositories) {
        this.repositories = repositories;
    }

    public UserModel() {
    }
}
