package com.keisar.Library.Management.Application.service;

import com.keisar.Library.Management.Application.dto.request.IssueRecordRequestDTO;
import com.keisar.Library.Management.Application.dto.response.IssueRecordResponseDTO;
import com.keisar.Library.Management.Application.model.Book;
import com.keisar.Library.Management.Application.model.User;
import com.keisar.Library.Management.Application.model.IssueRecord;
import com.keisar.Library.Management.Application.repository.BookRepository;
import com.keisar.Library.Management.Application.repository.IssueRecordRepository;
import com.keisar.Library.Management.Application.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class IssueRecordService {

    private final BookRepository bookRepository;
    private final IssueRecordRepository issueRecordRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public IssueRecordResponseDTO issueTheBook(IssueRecordRequestDTO issueDTO) {
        Book book = bookRepository.findById(issueDTO.getBookId()).orElseThrow(()-> new RuntimeException("Book Not Found"));
        User user = userRepository.findById(issueDTO.getUserId()).orElseThrow(()-> new RuntimeException("user not found"));;

        if(!book.isAvailable()){
            throw new RuntimeException("Book Not Available");
        }
        book.setQuantity(book.getQuantity()-1);

        IssueRecord issueRecord = new IssueRecord();

        issueRecord.setBook(book);
        issueRecord.setUser(user);
        issueRecord.setIssueDate(LocalDateTime.now());
        issueRecord.setDueDate(issueRecord.getIssueDate().plusDays(7));

        return modelMapper.map(issueRecordRepository.save(issueRecord), IssueRecordResponseDTO.class);
    }


    public Boolean isBookAvailabe(Book book) {
        
    }


}
