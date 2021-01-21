package com.nguyen.demo.core.repository;

import com.nguyen.demo.core.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
