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

    @Query("select r.id from RepositoryModel r where r.owner = :owner")
    Set<Long> getRepoIdsByOwner(@Param("owner") UserModel owner);

    @Query("select r.id from RepositoryModel r where r.owner = :owner and r.isTracked = true")
    Set<Long> getRepoIdsByIsTracked(@Param("owner") UserModel owner);

    @Query("select r from RepositoryModel r where r.owner = :owner and r.isTracked = true")
    List<RepositoryModel> getReposByIsTracked(@Param("owner") UserModel owner);
}
