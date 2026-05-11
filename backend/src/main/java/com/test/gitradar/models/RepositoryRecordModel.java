package com.test.gitradar.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "repository_records")

public class RepositoryRecordModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime lastSyncedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="repository_id")
    private RepositoryModel ownerRepo;

    private String testString;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getTestString() {
        return testString;
    }

    public void setTestString(String testString) {
        this.testString = testString;
    }

    public RepositoryRecordModel() {
    }
}
