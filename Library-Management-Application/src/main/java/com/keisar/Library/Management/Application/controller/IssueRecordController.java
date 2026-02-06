package com.keisar.Library.Management.Application.controller;


import com.keisar.Library.Management.Application.dto.request.IssueRecordRequestDTO;
import com.keisar.Library.Management.Application.dto.response.IssueRecordResponseDTO;
import com.keisar.Library.Management.Application.service.IssueRecordService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bookissues")
public class IssueRecordController {

    private final IssueRecordService issueRecordService;

    @PostMapping("/issuethebook")
    public ResponseEntity<IssueRecordResponseDTO> issueTheBook(@Valid @RequestBody IssueRecordRequestDTO issueDTO){
        return ResponseEntity.ok(issueRecordService.issueTheBook(issueDTO));
    }

    @PutMapping("/returnthebook/{issueId}")
    public ResponseEntity<IssueRecordResponseDTO> returnTheBook(@PathVariable @Min(value = 1, message = "Invalid Issue ID") Long issueId){
        return ResponseEntity.ok(issueRecordService.returnTheBook(issueId));
    }

    @GetMapping("/userhistory/{userId}")
    public ResponseEntity<List<IssueRecordResponseDTO>> getUserHistory(@PathVariable @Min(value = 1, message = "Invalid Issue ID") Long userId) {
        return ResponseEntity.ok(issueRecordService.getUserHistory(userId));
    }

}
