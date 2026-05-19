package com.test.gitradar.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "repository_records")

public class RepositoryRecordModel {

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof RepositoryRecordModel that)) return false;
        if (id == null || that.id == null) return false;
        return java.util.Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hashCode(id);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime lastSyncedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="repository_id")
    private RepositoryModel ownerRepo;

    @OneToMany(mappedBy = "ownerRecord", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RepositoryLanguageModel> repositoryLanguages = new HashSet<>();

    public void addRepositoryLanguage(RepositoryLanguageModel repositoryLanguage) {
        this.repositoryLanguages.add(repositoryLanguage);
        repositoryLanguage.setOwnerRecord(this);
    }

    private String languagesUrl;

    private String commitsUrl;

    private LocalDateTime pushedAt;

    private LocalDateTime updatedAt;

    private Integer stargazersCount;

    private Integer watchersCount;

    private Integer sizeKb;

    private Integer openIssuesCount;

    private Integer forksCount;

    private Integer subscribersCount;

    public Long getId() {
        return id;
    }

    public LocalDateTime getLastSyncedAt() {
        return lastSyncedAt;
    }

    public void setLastSyncedAt(LocalDateTime lastSyncedAt) {
        this.lastSyncedAt = lastSyncedAt;
    }

    public RepositoryModel getOwnerRepo() {
        return ownerRepo;
    }

    public void setOwnerRepo(RepositoryModel ownerRepo) {
        this.ownerRepo = ownerRepo;
    }

    public String getLanguagesUrl() {
        return languagesUrl;
    }

    public void setLanguagesUrl(String languagesUrl) {
        this.languagesUrl = languagesUrl;
    }

    public String getCommitsUrl() {
        return commitsUrl;
    }

    public void setCommitsUrl(String commitsUrl) {
        this.commitsUrl = commitsUrl;
    }

    public LocalDateTime getPushedAt() {
        return pushedAt;
    }

    public void setPushedAt(LocalDateTime pushedAt) {
        this.pushedAt = pushedAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getStargazersCount() {
        return stargazersCount;
    }

    public void setStargazersCount(Integer stargazersCount) {
        this.stargazersCount = stargazersCount;
    }

    public Integer getWatchersCount() {
        return watchersCount;
    }

    public void setWatchersCount(Integer watchersCount) {
        this.watchersCount = watchersCount;
    }

    public Integer getSizeKb() {
        return sizeKb;
    }

    public void setSizeKb(Integer sizeKb) {
        this.sizeKb = sizeKb;
    }

    public Integer getOpenIssuesCount() {
        return openIssuesCount;
    }

    public void setOpenIssuesCount(Integer openIssuesCount) {
        this.openIssuesCount = openIssuesCount;
    }

    public Integer getForksCount() {
        return forksCount;
    }

    public void setForksCount(Integer forksCount) {
        this.forksCount = forksCount;
    }

    public Integer getSubscribersCount() {
        return subscribersCount;
    }

    public void setSubscribersCount(Integer subscribersCount) {
        this.subscribersCount = subscribersCount;
    }
}
