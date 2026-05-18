package com.test.gitradar.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="repositories")

public class RepositoryModel {

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof RepositoryModel that)) return false;
        return java.util.Objects.equals(repoId, that.repoId);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hashCode(repoId);
    }

    @Id
    private Long repoId;

    private String name;

    private LocalDateTime lastSyncedAt;

    private boolean isTracked;

    @ManyToOne
    @JoinColumn(name="owner_id")
    private UserModel owner;

    @OneToMany(mappedBy = "ownerRepo", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RepositoryRecordModel> repositoryRecords = new HashSet<>();

    public void clearRepositoryRecords() {
        this.repositoryRecords.clear();
    }

    public void addRepositoryRecord(RepositoryRecordModel repositoryRecord) {
        this.repositoryRecords.add(repositoryRecord);
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

    public Set<RepositoryRecordModel> getRepositoryRecords() {
        return repositoryRecords;
    }

    public void setRepositoryRecords(Set<RepositoryRecordModel> repositoryRecords) {
        this.repositoryRecords = repositoryRecords;
    }

    public RepositoryModel() {
    }
}
