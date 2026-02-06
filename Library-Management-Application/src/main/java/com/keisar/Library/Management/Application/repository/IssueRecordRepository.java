package com.keisar.Library.Management.Application.repository;

import com.keisar.Library.Management.Application.model.IssueRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueRecordRepository extends JpaRepository<IssueRecord, Long> {
    List<IssueRecord> findAllByUserIdOrderByIssueDateDesc(Long userId);
}
