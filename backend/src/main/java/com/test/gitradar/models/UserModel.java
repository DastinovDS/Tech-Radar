package com.test.gitradar.models;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
public class UserModel {
    @Id
    private Long githubId;

    private String login;
    private String accessToken;
    private String avatarUrl;

    private int followers;
    private int following;
    private int totalPrivateRepos;

    private LocalDateTime lastSync;
}
