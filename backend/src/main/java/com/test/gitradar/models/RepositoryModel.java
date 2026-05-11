package com.test.gitradar.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="repositories")

public class RepositoryModel {

    @Id
    private Long repoId;

    private String name;

    private LocalDateTime lastSyncedAt;

    private boolean isTracked;

    @ManyToOne
    @JoinColumn(name="owner_id")
    private UserModel owner;

    @OneToMany(mappedBy = "ownerRepo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RepositoryRecordModel> repositoryRecords = new ArrayList<>();

    public void clearRepositoryRecords() {
        this.repositoryRecords.clear();
    }

    public Long getRepoId() {
        return repoId;
    }

    public void setRepoId(Long repoId) {
        this.repoId = repoId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getLastSyncedAt() {
        return lastSyncedAt;
    }

    public void setLastSyncedAt(LocalDateTime lastSyncedAt) {
        this.lastSyncedAt = lastSyncedAt;
    }

    public boolean isTracked() {
        return isTracked;
    }

    public void setTracked(boolean tracked) {
        isTracked = tracked;
    }

    public UserModel getOwner() {
        return owner;
    }

    public void setOwner(UserModel owner) {
        this.owner = owner;
    }

    public List<RepositoryRecordModel> getRepositoryRecords() {
        return repositoryRecords;
    }

    public void setRepositoryRecords(List<RepositoryRecordModel> repositoryRecords) {
        this.repositoryRecords = repositoryRecords;
    }

    public RepositoryModel() {
    }
}
