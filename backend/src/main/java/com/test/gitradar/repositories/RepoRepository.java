package com.test.gitradar.repositories;

import com.test.gitradar.models.RepositoryModel;
import com.test.gitradar.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;


@Repository
public interface RepoRepository extends JpaRepository<RepositoryModel, Long> {

    @Query("select repo from RepositoryModel repo where repo.owner = :owner and repo.repoId = :repoId")
    RepositoryModel getRepoByOwner(
            @Param("owner") UserModel owner,
            @Param("repoId") Long repoId
    );

    Set<RepositoryModel> findAllByOwnerAndRepoIdIn(UserModel owner, List<Long> ids);

    @Query("SELECT repo FROM RepositoryModel repo WHERE repo.isTracked = true AND repo.owner.githubId = :userId")
    Set<RepositoryModel> findAllTrackedByUserId(@Param("userId") Long userId);
}
