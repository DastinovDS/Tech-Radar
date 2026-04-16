package com.test.gitradar.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")

public class UserModel {
    @Id
    private Long githubId;

    private String login;
    private String accessToken;
    private String avatarUrl;
    private String email;

    private int followers;
    private int following;
    private int totalPrivateRepos;

    private LocalDateTime lastSync;

    public Long getGithubId() {
        return githubId;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
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

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public int getFollowing() {
        return following;
    }

    public void setFollowing(int following) {
        this.following = following;
    }

    public int getTotalPrivateRepos() {
        return totalPrivateRepos;
    }

    public void setTotalPrivateRepos(int totalPrivateRepos) {
        this.totalPrivateRepos = totalPrivateRepos;
    }

    public LocalDateTime getLastSync() {
        return lastSync;
    }

    public void setLastSync(LocalDateTime lastSync) {
        this.lastSync = lastSync;
    }

    public UserModel() {
    }

    public UserModel(Long githubId,
                     String login,
                     String accessToken,
                     String avatarUrl,
                     String email,
                     int followers,
                     int following,
                     int totalPrivateRepos,
                     LocalDateTime lastSync) {

        this.githubId = githubId;
        this.login = login;
        this.accessToken = accessToken;
        this.avatarUrl = avatarUrl;
        this.email = email;
        this.followers = followers;
        this.following = following;
        this.totalPrivateRepos = totalPrivateRepos;
        this.lastSync = lastSync;
    }
}
