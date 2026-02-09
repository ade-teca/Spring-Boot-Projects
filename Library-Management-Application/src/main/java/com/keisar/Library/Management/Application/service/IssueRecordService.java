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
import java.util.List;


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

        IssueRecord savedRecord = issueRecordRepository.save(issueRecord);

        // Mapeamento Manual (Totalmente seguro)
        IssueRecordResponseDTO response = new IssueRecordResponseDTO();
        response.setId(savedRecord.getId());
        response.setUserName(user.getUsername()); // Você já tem o objeto 'user'
        response.setBookTitle(book.getTitle());   // Você já tem o objeto 'book'
        response.setBookAuthor(book.getAuthor());
        response.setIssueDate(savedRecord.getIssueDate());
        response.setDueDate(savedRecord.getDueDate());
        response.setReturned(savedRecord.isReturned());

        return response;
    }


    @Transactional
    public IssueRecordResponseDTO returnTheBook(Long issueId) {
        IssueRecord issueRecord = issueRecordRepository.findById(issueId)
                .orElseThrow(() -> new RuntimeException("Issue Record Not Found"));

        if (issueRecord.isReturned()) {
            throw new RuntimeException("Book already returned");
        }

        Book book = issueRecord.getBook();
        book.setQuantity(book.getQuantity() + 1);

        issueRecord.setReturnDate(LocalDateTime.now());
        issueRecord.setReturned(true);

        IssueRecord savedRecord = issueRecordRepository.save(issueRecord);

        // Mapeamento Manual para garantir que os nomes fiquem corretos
        IssueRecordResponseDTO response = new IssueRecordResponseDTO();
        response.setId(savedRecord.getId());
        response.setUserName(savedRecord.getUser().getUsername());
        response.setBookTitle(savedRecord.getBook().getTitle());
        response.setBookAuthor(savedRecord.getBook().getAuthor());
        response.setIssueDate(savedRecord.getIssueDate());
        response.setDueDate(savedRecord.getDueDate());
        response.setReturnDate(savedRecord.getReturnDate()); // Agora com data de retorno
        response.setReturned(savedRecord.isReturned());

        return response;
    }

    public List<IssueRecordResponseDTO> getUserHistory(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User not found");
        }

        return issueRecordRepository.findAllByUserIdOrderByIssueDateDesc(userId)
                .stream()
                .map(record -> {
                    IssueRecordResponseDTO dto = new IssueRecordResponseDTO();
                    dto.setId(record.getId());
                    dto.setUserName(record.getUser().getUsername());
                    dto.setBookTitle(record.getBook().getTitle());
                    dto.setBookAuthor(record.getBook().getAuthor());
                    dto.setIssueDate(record.getIssueDate());
                    dto.setDueDate(record.getDueDate());
                    dto.setReturnDate(record.getReturnDate());
                    dto.setReturned(record.isReturned());
                    return dto;
                })
                .toList();
    }


}