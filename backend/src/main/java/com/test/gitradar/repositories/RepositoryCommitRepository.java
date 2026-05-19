package com.test.gitradar.repositories;

import com.test.gitradar.models.RepositoryCommitModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryCommitRepository extends JpaRepository<RepositoryCommitModel, Long> {
}
