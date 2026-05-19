package com.test.gitradar.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="repository_commits")
public class RepositoryCommitModel {

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof RepositoryCommitModel that)) return false;
        return java.util.Objects.equals(repo_commit_id, that.repo_commit_id);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hashCode(repo_commit_id);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long repo_commit_id;

    private String sha;
    private LocalDateTime commitDate;

    @ManyToOne
    @JoinColumn(name="repository_id")
    private RepositoryModel ownerRepo;

    public Long getRepo_commit_id() {
        return repo_commit_id;
    }

    public void setRepo_commit_id(Long repo_commit_id) {
        this.repo_commit_id = repo_commit_id;
    }

    public String getSha() {
        return sha;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }

    public LocalDateTime getCommitDate() {
        return commitDate;
    }

    public void setCommitDate(LocalDateTime commitDate) {
        this.commitDate = commitDate;
    }

    public RepositoryModel getOwnerRepo() {
        return ownerRepo;
    }

    public void setOwnerRepo(RepositoryModel ownerRepo) {
        this.ownerRepo = ownerRepo;
    }
}
