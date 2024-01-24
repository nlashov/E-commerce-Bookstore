package com.nl.Nutso.repository;

import com.nl.Nutso.model.entity.BookEntity;
import com.nl.Nutso.model.enums.CategoryEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookRepository extends
        JpaRepository<BookEntity, Long>,
        JpaSpecificationExecutor<BookEntity> {

    Optional<BookEntity> findBookByUuid(UUID uuid);

    Page<BookEntity> findByCategory(CategoryEnum category, Pageable pageable);

    Page<BookEntity> findByIsAvailableTrue(Pageable pageable);

    Page<BookEntity> findByIsAvailableFalse(Pageable pageable);

    List<BookEntity> findByCategoryAndUuidNot(CategoryEnum category, UUID uuid);
}
