package com.test.gitradar.repositories;

import com.test.gitradar.models.RepositoryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepoRepository extends JpaRepository<RepositoryModel, Long> {
}
