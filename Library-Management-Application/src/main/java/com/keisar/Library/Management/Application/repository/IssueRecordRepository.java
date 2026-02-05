package com.keisar.Library.Management.Application.repository;

import com.keisar.Library.Management.Application.model.IssueRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IssueRecordRepository extends JpaRepository<IssueRecord, Long> {
}
