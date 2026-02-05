package com.keisar.Library.Management.Application.dto.response;

import lombok.Data;

@Data
public class BookResponseDTO {

    private Long id;
    private String title;
    private String author;
    private String isbn;
    private Long quantity;
    private boolean isAvailable;
}
