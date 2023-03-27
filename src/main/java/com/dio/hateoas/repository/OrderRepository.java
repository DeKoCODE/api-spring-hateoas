package com.dio.hateoas.repository;

import com.dio.hateoas.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
