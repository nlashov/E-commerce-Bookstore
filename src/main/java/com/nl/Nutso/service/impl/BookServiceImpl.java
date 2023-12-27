package com.nl.Nutso.service.impl;

import com.nl.Nutso.model.dto.AddBookDTO;
import com.nl.Nutso.model.dto.BookDetailDTO;
import com.nl.Nutso.model.dto.BookSummaryDTO;
import com.nl.Nutso.model.entity.BookEntity;
import com.nl.Nutso.repository.BookRepository;
import com.nl.Nutso.service.BookService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public UUID addBook(AddBookDTO addBookDTO) {
        BookEntity newBook = map(addBookDTO);
        bookRepository.save(newBook);
        return newBook.getUuid();
    }

    @Override
    public Page<BookSummaryDTO> getAllBooks(Pageable pageable) {
        return null;
    }

    @Override
    public Optional<BookDetailDTO> getBookDetail(UUID bookUuid) {
        return Optional.empty();
    }

    @Override
    @Transactional
    public void deleteBook(UUID bookUuid) {
        bookRepository.deleteById(bookUuid);
    }

    public static BookEntity map(AddBookDTO addBookDTO) {
        return new BookEntity()
                .setTitle(addBookDTO.title())
                .setAuthor(addBookDTO.author())
                .setPrice(addBookDTO.price())
                .setCondition(addBookDTO.condition())
                .setAdditionalInfo(addBookDTO.additionalInfo())
                .setCategory(addBookDTO.category())
                .setYear(addBookDTO.year())
                .setImageUrl(addBookDTO.imageUrl());
    }
}
