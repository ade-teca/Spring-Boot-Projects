package com.keisar.Library.Management.Application.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class IssueRecordResponseDTO {
    private Long id;
    private String userName;
    private String bookTitle;
    private String bookAuthor;
    private LocalDateTime issueDate;
    private LocalDateTime dueDate;
    private LocalDateTime returnDate;
    private boolean returned;
}
