package com.test.gitradar.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="repositories")
@Data
public class RepositoryModel {
    @Id
    private Long id;

    private String name;
    private String fullName;
    private String description;
    private String languages;
    private int stargazersCount;
    private String htmlUrl;

    @ManyToOne
    @JoinColumn(name="owner_id")
    private UserModel owner;
}
