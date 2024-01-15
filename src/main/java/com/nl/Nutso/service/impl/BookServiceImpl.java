package com.nl.Nutso.service.impl;

import com.nl.Nutso.model.dto.AddBookDTO;
import com.nl.Nutso.model.dto.BookDetailDTO;
import com.nl.Nutso.model.dto.BookSummaryDTO;
import com.nl.Nutso.model.dto.SearchBookDTO;
import com.nl.Nutso.model.entity.BookEntity;
import com.nl.Nutso.model.enums.CategoryEnum;
import com.nl.Nutso.repository.BookRepository;
import com.nl.Nutso.repository.BookSpecification;
import com.nl.Nutso.service.BookService;
import com.nl.Nutso.service.exception.ObjectNotFoundException;
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
    public BookEntity getBookByUuid(UUID bookUuid) {
        return bookRepository.findBookByUuid(bookUuid)
                .orElseThrow(() -> new ObjectNotFoundException("Book with uuid " + bookUuid + " not found."));
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
    public void deactivateBook(UUID bookUuid) {
        BookEntity book = bookRepository.findBookByUuid(bookUuid)
                .orElseThrow(() -> new ObjectNotFoundException("Book with id " + bookUuid + " not found!"));

        book.setAvailable(false);
        bookRepository.save(book);
    }

    @Override
    public Page<BookSummaryDTO> searchBooks(SearchBookDTO searchBookDTO, Pageable pageable) {
        Page<BookEntity> booksPage = bookRepository.findAll(BookSpecification.withTitleAndAuthor(searchBookDTO), pageable);

        return booksPage.map(BookServiceImpl::mapAsSummary);
    }

    @Override
    public Page<BookSummaryDTO> getBooksByCategory(CategoryEnum category, Pageable pageable) {
        return bookRepository.findByCategory(category, pageable)
                .map(BookServiceImpl::mapAsSummary);
    }

    @Override
    public void updateBook(UUID uuid, BookDetailDTO bookDetailDTO) {
        // Fetch the book entity from the repository
        BookEntity book = bookRepository.findBookByUuid(uuid)
                .orElseThrow(() -> new ObjectNotFoundException("Book with id " + uuid + " not found!"));

        // Update the book entity with data from the BookEditDTO
        book.setTitle(bookDetailDTO.title());
        book.setAuthor(bookDetailDTO.author());
        book.setPrice(bookDetailDTO.price());
        book.setCategory(bookDetailDTO.category());
        book.setBookCondition(bookDetailDTO.bookCondition());
        book.setYear(bookDetailDTO.year());
        book.setImageUrl(bookDetailDTO.imageUrl());
        book.setAdditionalInfo(bookDetailDTO.additionalInfo());
        book.setAvailable(bookDetailDTO.isAvailable());

        bookRepository.save(book);
    }

    @Override
    public Page<BookSummaryDTO> getAvailableBooks(Pageable pageable) {
        return bookRepository.findByIsAvailableTrue(pageable).map(BookServiceImpl::mapAsSummary);
    }

    @Override
    public Page<BookSummaryDTO> getUnavailableBooks(Pageable pageable) {
        return bookRepository.findByIsAvailableFalse(pageable).map(BookServiceImpl::mapAsSummary);
    }

    @Override
    public Page<BookSummaryDTO> getRelatedBooks(Long categoryId, Pageable pageable) {
        return bookRepository.findRelatedBooks(categoryId, pageable).map(BookServiceImpl::mapAsSummary);
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
                .setImageUrl(addBookDTO.imageUrl())
                .setAvailable(true);
    }


    private static BookDetailDTO mapAsDetail(BookEntity bookEntity) {
        return new BookDetailDTO(
                bookEntity.getUuid(),
                bookEntity.getTitle(),
                bookEntity.getAuthor(),
                bookEntity.getPrice(),
                bookEntity.getBookCondition(),
                bookEntity.getAdditionalInfo(),
                bookEntity.getCategory(),
                bookEntity.getYear(),
                bookEntity.getImageUrl(),
                bookEntity.isAvailable());

    }

    private static BookSummaryDTO mapAsSummary(BookEntity bookEntity) {
        return new BookSummaryDTO(
                bookEntity.getUuid().toString(),
                bookEntity.getTitle(),
                bookEntity.getAuthor(),
                bookEntity.getPrice(),
                bookEntity.getImageUrl(),
                bookEntity.isAvailable());
    }


}
