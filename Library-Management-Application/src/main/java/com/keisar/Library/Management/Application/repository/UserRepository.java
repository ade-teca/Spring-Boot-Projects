package com.keisar.Library.Management.Application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.keisar.Library.Management.Application.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
