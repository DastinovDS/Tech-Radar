package com.test.gitradar.repositories;

import com.test.gitradar.models.RepositoryModel;
import com.test.gitradar.models.RepositoryRecordModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface RepositoryRecordRepository extends JpaRepository<RepositoryRecordModel, Long> {
    @Modifying
    @Transactional
    @Query("delete from RepositoryRecordModel r where r.ownerRepo = :repository")
    void deleteByRepository(@Param("repository") RepositoryModel repository);
}
