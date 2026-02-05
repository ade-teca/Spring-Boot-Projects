package com.keisar.Library.Management.Application.repository;

import com.keisar.Library.Management.Application.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
