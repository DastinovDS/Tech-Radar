package com.test.gitradar.models;

import jakarta.persistence.*;

@Entity
@Table(name="repositories")

public class RepositoryModel {
    @Id
    private Long id;

    private String name;
    private String fullName;
    private String description;
    private String languages;
    private int stargazersCount;
    private String htmlUrl;
    private int watchers;

    @ManyToOne
    @JoinColumn(name="owner_id")
    private UserModel owner;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getWatchers() {
        return watchers;
    }

    public void setWatchers(int watchers) {
        this.watchers = watchers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public int getStargazersCount() {
        return stargazersCount;
    }

    public void setStargazersCount(int stargazersCount) {
        this.stargazersCount = stargazersCount;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    public UserModel getOwner() {
        return owner;
    }

    public void setOwner(UserModel owner) {
        this.owner = owner;
    }

    public RepositoryModel() {
    }

    public RepositoryModel(Long id,
                           String name,
                           String fullName,
                           String description,
                           String languages,
                           int stargazersCount,
                           int watchers,
                           String htmlUrl,
                           UserModel owner) {
        this.id = id;
        this.name = name;
        this.fullName = fullName;
        this.description = description;
        this.languages = languages;
        this.stargazersCount = stargazersCount;
        this.htmlUrl = htmlUrl;
        this.owner = owner;
        this.watchers = watchers;
    }
}
