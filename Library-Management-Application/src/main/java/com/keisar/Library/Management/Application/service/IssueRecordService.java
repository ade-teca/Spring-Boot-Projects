package com.keisar.Library.Management.Application.service;

import com.keisar.Library.Management.Application.dto.request.IssueRecordRequestDTO;
import com.keisar.Library.Management.Application.dto.response.IssueRecordResponseDTO;
import com.keisar.Library.Management.Application.exception.ResourceNotFoundException;
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
        // 1. Busca as entidades (Dica: alterei a mensagem do erro do Book)
        Book book = bookRepository.findById(issueDTO.getBookId())
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));
        User user = userRepository.findById(issueDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // 2. Validação de negócio
        if (book.getQuantity() <= 0) {
            throw new RuntimeException("Book Not Available");
        }

        // 3. Atualiza estoque
        book.setQuantity(book.getQuantity() - 1);
        bookRepository.save(book); // Garante a persistência da alteração

        // 4. Cria o registro
        IssueRecord issueRecord = new IssueRecord();
        issueRecord.setBook(book);
        issueRecord.setUser(user);
        issueRecord.setIssueDate(LocalDateTime.now());
        issueRecord.setDueDate(issueRecord.getIssueDate().plusDays(7));

        IssueRecord savedRecord = issueRecordRepository.save(issueRecord);

        // 5. Mapeamento Manual (Seguro contra NULLs)
        IssueRecordResponseDTO response = new IssueRecordResponseDTO();
        response.setId(savedRecord.getId());
        response.setUserName(user.getUsername());
        response.setBookTitle(book.getTitle());
        response.setBookAuthor(book.getAuthor());
        response.setIssueDate(savedRecord.getIssueDate());
        response.setDueDate(savedRecord.getDueDate());
        response.setReturned(savedRecord.isReturned());

        return response;
    }


    @Transactional
    public IssueRecordResponseDTO returnTheBook(Long issueId) {
        IssueRecord issueRecord = issueRecordRepository.findById(issueId)
                .orElseThrow(() -> new ResourceNotFoundException("Issue Record Not Found"));

        if (issueRecord.isReturned()) {
            throw new RuntimeException("Book already returned");
        }

        Book book = issueRecord.getBook();
        book.setQuantity(book.getQuantity() + 1);

        issueRecord.setReturnDate(LocalDateTime.now());
        issueRecord.setReturned(true);

        return modelMapper.map(issueRecordRepository.save(issueRecord), IssueRecordResponseDTO.class);
    }

    public List<IssueRecordResponseDTO> getUserHistory(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found");
        }

        return issueRecordRepository.findAllByUserIdOrderByIssueDateDesc(userId)
                .stream()
                .map(record -> modelMapper.map(record, IssueRecordResponseDTO.class))
                .toList();
    }


}
