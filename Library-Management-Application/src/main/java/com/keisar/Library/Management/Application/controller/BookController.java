package com.keisar.Library.Management.Application.controller;

import com.keisar.Library.Management.Application.dto.request.BookRequestDTO;
import com.keisar.Library.Management.Application.dto.response.BookResponseDTO;
import com.keisar.Library.Management.Application.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping
    public ResponseEntity<List<BookResponseDTO>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping("/search/{term}")
    public ResponseEntity<List<BookResponseDTO>> searchBooks(@PathVariable String term) {
        return ResponseEntity.ok(bookService.searchBooks(term));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<BookResponseDTO> getBookById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BookResponseDTO> createBook(@RequestBody BookRequestDTO bookRequestDTO) {
        return ResponseEntity.status(201).body(bookService.createBook(bookRequestDTO));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BookResponseDTO> updateBook(@PathVariable Long id, @RequestBody BookRequestDTO bookRequestDTO) {
        return ResponseEntity.ok(bookService.updateBook(id, bookRequestDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBookById(id);
        return ResponseEntity.noContent().build();
    }
}
