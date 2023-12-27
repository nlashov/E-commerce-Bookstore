package com.nl.Nutso.repository;

import com.nl.Nutso.model.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository {
Optional<BookEntity> findBookById(Long id);
void deleteById(Long id);
}
