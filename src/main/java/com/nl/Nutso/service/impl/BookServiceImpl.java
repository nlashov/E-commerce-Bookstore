package com.nl.Nutso.service.impl;

import com.nl.Nutso.model.dto.AddBookDTO;
import com.nl.Nutso.model.dto.BookDetailDTO;
import com.nl.Nutso.model.dto.SearchBookDTO;
import com.nl.Nutso.model.dto.BookSummaryDTO;
import com.nl.Nutso.model.entity.BookEntity;
import com.nl.Nutso.repository.BookRepository;
import com.nl.Nutso.repository.BookSpecification;
import com.nl.Nutso.service.BookService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public UUID addBook(AddBookDTO addBookDTO) {
        BookEntity newBook = map(addBookDTO);
        newBook = bookRepository.save(newBook);
        return newBook.getUuid();
    }

    @Override
    public Page<BookSummaryDTO> getAllBooks(Pageable pageable) {
        return bookRepository
                .findAll(pageable)
                .map(BookServiceImpl::mapAsSummary);
    }

    @Override
    public Optional<BookDetailDTO> getBookDetail(UUID bookUuid) {
        return bookRepository.findBookByUuid(bookUuid).map(BookServiceImpl::mapAsDetail);
    }

    @Override
    @Transactional
    public void deleteBook(UUID bookUuid) {
        bookRepository.deleteByUuid(bookUuid);
    }

    @Override
    public List<BookDetailDTO> searchBooks(SearchBookDTO searchBookDTO) {
        List<BookEntity> books = bookRepository.findAll(BookSpecification.withTitleAndAuthor(searchBookDTO));

        return books.stream()
                .map(BookServiceImpl::mapAsDetail)
                .collect(Collectors.toList());
    }


    public static BookEntity map(AddBookDTO addBookDTO) {
        return new BookEntity()
                .setUuid(UUID.randomUUID())
                .setTitle(addBookDTO.title())
                .setAuthor(addBookDTO.author())
                .setPrice(addBookDTO.price())
                .setBookCondition(addBookDTO.bookCondition())
                .setAdditionalInfo(addBookDTO.additionalInfo())
                .setCategory(addBookDTO.category())
                .setYear(addBookDTO.year())
                .setImageUrl(addBookDTO.imageUrl());
    }

    private static BookDetailDTO mapAsDetail(BookEntity bookEntity) {
        return new BookDetailDTO(
                bookEntity.getUuid().toString(),
                bookEntity.getTitle(),
                bookEntity.getAuthor(),
                bookEntity.getPrice(),
                bookEntity.getBookCondition(),
                bookEntity.getAdditionalInfo(),
                bookEntity.getCategory(),
                bookEntity.getYear(),
                bookEntity.getImageUrl());

    }

    private static BookSummaryDTO mapAsSummary(BookEntity bookEntity) {
        return new BookSummaryDTO(
                bookEntity.getUuid().toString(),
                bookEntity.getTitle(),
                bookEntity.getAuthor(),
                bookEntity.getPrice(),
                bookEntity.getImageUrl());
    }


}
