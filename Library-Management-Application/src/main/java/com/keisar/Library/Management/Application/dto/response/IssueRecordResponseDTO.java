package com.keisar.Library.Management.Application.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class IssueRecordResponseDTO {

    private Long id;
    private LocalDateTime issueDate;
    private LocalDateTime dueDate;
    private LocalDateTime returnDate;
    private boolean isReturned;

    private String userName;
    private String bookTitle;
    private String bookAuthor;
}
