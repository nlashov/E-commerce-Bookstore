package com.nl.Nutso.service;

import com.nl.Nutso.model.dto.AddBookDTO;
import com.nl.Nutso.model.dto.BookDetailDTO;
import com.nl.Nutso.model.dto.BookSummaryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;


public interface BookService {

    UUID addBook(AddBookDTO addBookDTO);
    Page<BookSummaryDTO> getAllBooks(Pageable pageable);

    Optional<BookDetailDTO> getBookDetail(UUID bookUuid);

    void deleteBook(UUID bookId);


}
