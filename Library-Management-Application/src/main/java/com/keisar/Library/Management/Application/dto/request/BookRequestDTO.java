package com.keisar.Library.Management.Application.dto.request;

import lombok.Data;

@Data
public class BookRequestDTO {

    private String title;
    private String author;
    private String isbn;
    private Long quantity;
}
