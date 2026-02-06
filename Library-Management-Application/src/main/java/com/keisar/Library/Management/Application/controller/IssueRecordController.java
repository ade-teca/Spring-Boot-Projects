package com.keisar.Library.Management.Application.controller;


import com.keisar.Library.Management.Application.dto.request.IssueRecordRequestDTO;
import com.keisar.Library.Management.Application.dto.response.IssueRecordResponseDTO;
import com.keisar.Library.Management.Application.service.IssueRecordService;
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
    public ResponseEntity<IssueRecordResponseDTO> issueTheBook(@RequestBody IssueRecordRequestDTO issueDTO){
        return ResponseEntity.ok(issueRecordService.issueTheBook(issueDTO));
    }

    @PutMapping("/returnthebook/{issueId}")
    public ResponseEntity<IssueRecordResponseDTO> returnTheBook(@PathVariable Long issueId){
        return ResponseEntity.ok(issueRecordService.returnTheBook(issueId));
    }

    @GetMapping("/userhistory/{userId}")
    public ResponseEntity<List<IssueRecordResponseDTO>> getUserHistory(@PathVariable Long userId) {
        return ResponseEntity.ok(issueRecordService.getUserHistory(userId));
    }

}
