package com.nl.Nutso.repository;

import com.nl.Nutso.model.dto.SearchBookDTO;
import com.nl.Nutso.model.entity.BookEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class BookSpecification {

    public static Specification<BookEntity> withTitleAndAuthor(SearchBookDTO searchBookDTO) {
        return (root, query, criteriaBuilder) -> {
            Predicate p = criteriaBuilder.conjunction();
            if (searchBookDTO.query() != null && !searchBookDTO.query().trim().isEmpty()) {
                String queryString = "%" + searchBookDTO.query().toLowerCase() + "%";
                p = criteriaBuilder.or(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), queryString),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("author")), queryString)
                );
            }
            return p;
        };
    }
}

