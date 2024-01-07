package com.nl.Nutso.service;

import com.nl.Nutso.model.dto.AddBookDTO;
import com.nl.Nutso.model.dto.BookDetailDTO;
import com.nl.Nutso.model.dto.SearchBookDTO;
import com.nl.Nutso.model.dto.BookSummaryDTO;
import com.nl.Nutso.model.entity.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface BookService {

    UUID addBook(AddBookDTO addBookDTO);

    Page<BookSummaryDTO> getAllBooks(Pageable pageable);

    Optional<BookDetailDTO> getBookDetail(UUID bookUuid);

    void deleteBook(UUID bookId);

    Page<BookSummaryDTO> searchBooks(SearchBookDTO searchBookDTO, Pageable pageable);

}
