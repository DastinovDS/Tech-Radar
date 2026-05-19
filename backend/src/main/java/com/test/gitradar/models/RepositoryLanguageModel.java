package com.test.gitradar.models;

import jakarta.persistence.*;

@Entity
@Table(name="repository_languages")
public class RepositoryLanguageModel {

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof RepositoryLanguageModel that)) return false;
        return java.util.Objects.equals(repo_language_id, that.repo_language_id);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hashCode(repo_language_id);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long repo_language_id;

    private String name;
    private Long size;

    @ManyToOne
    @JoinColumn(name="parent_repo")
    private RepositoryRecordModel ownerRecord;

    public Long getRepo_language_id() {
        return repo_language_id;
    }

    public void setRepo_language_id(Long repo_language_id) {
        this.repo_language_id = repo_language_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public RepositoryRecordModel getOwnerRecord() {
        return ownerRecord;
    }

    public void setOwnerRecord(RepositoryRecordModel ownerRecord) {
        this.ownerRecord = ownerRecord;
    }
}
