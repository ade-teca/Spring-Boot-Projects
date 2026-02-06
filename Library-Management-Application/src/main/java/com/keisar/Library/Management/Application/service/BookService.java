package com.keisar.Library.Management.Application.service;


import com.keisar.Library.Management.Application.dto.request.BookRequestDTO;
import com.keisar.Library.Management.Application.dto.response.BookResponseDTO;
import com.keisar.Library.Management.Application.exception.ResourceNotFoundException;
import com.keisar.Library.Management.Application.model.Book;
import com.keisar.Library.Management.Application.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;

    public BookResponseDTO createBook(BookRequestDTO bookRequestDTO){
        Book book = modelMapper.map(bookRequestDTO, Book.class);
        return modelMapper.map(bookRepository.save(book), BookResponseDTO.class);
    }

    public List<BookResponseDTO> getAllBooks(){
        return bookRepository.findAll().stream()
                .map(book -> modelMapper.map(book, BookResponseDTO.class))
                .collect(Collectors.toList());
    }

    public BookResponseDTO getBookById(Long bookId){
        Book book = bookRepository.findById(bookId).orElseThrow(()-> new ResourceNotFoundException("Book not found"));
        return modelMapper.map(book, BookResponseDTO.class);
    }

    public BookResponseDTO updateBook(Long id,BookRequestDTO bookRequestDTO){
        Book book = bookRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Book not found"));
        modelMapper.map(bookRequestDTO,book);
        return modelMapper.map(bookRepository.save(book), BookResponseDTO.class);
    }

    public void deleteBookById(Long bookId){
        if(!bookRepository.existsById(bookId)){
            throw new ResourceNotFoundException("Book not found");
        }
        bookRepository.deleteById(bookId);
    }

    public List<BookResponseDTO> searchBooks(String term) {
        return bookRepository
                .findAllByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(term, term)
                .stream()
                .map(book -> modelMapper.map(book, BookResponseDTO.class))
                .collect(Collectors.toList());
    }
}
