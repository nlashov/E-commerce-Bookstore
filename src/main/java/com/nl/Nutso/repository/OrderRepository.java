package com.nl.Nutso.repository;

import com.nl.Nutso.model.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findByIsAcceptFalse();
    List<OrderEntity> findByIsAcceptTrue();
}
