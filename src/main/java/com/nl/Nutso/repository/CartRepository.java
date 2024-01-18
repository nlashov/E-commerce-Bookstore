package com.nl.Nutso.repository;

import com.nl.Nutso.model.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, Long> {

    @Query("SELECT c FROM CartEntity c LEFT JOIN FETCH c.cartItems WHERE c.id = :cartId")
    Optional<CartEntity> findByIdWithItems(@Param("cartId") Long cartId);
}
