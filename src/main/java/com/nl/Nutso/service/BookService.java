package com.nl.Nutso.service;

import com.nl.Nutso.model.dto.AddBookDTO;
import com.nl.Nutso.model.dto.BookDetailDTO;
import com.nl.Nutso.model.dto.SearchBookDTO;
import com.nl.Nutso.model.dto.BookSummaryDTO;
import com.nl.Nutso.model.entity.BookEntity;
import com.nl.Nutso.model.enums.CategoryEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface BookService {

    UUID addBook(AddBookDTO addBookDTO);

    Page<BookSummaryDTO> getAllBooks(Pageable pageable);

    Optional<BookDetailDTO> getBookDetail(UUID bookUuid);

    void updateBook(UUID bookUuid, BookDetailDTO bookDetailDTO);

    void deactivateBook(UUID bookId);

    Page<BookSummaryDTO> searchBooks(SearchBookDTO searchBookDTO, Pageable pageable);

    Page<BookSummaryDTO> getBooksByCategory(CategoryEnum category, Pageable pageable);

    Page<BookSummaryDTO> getAvailableBooks(Pageable pageable);

    Page<BookSummaryDTO> getUnavailableBooks(Pageable pageable);
}
